package com.example.demo.photo

import com.example.demo.components.StorageComponent
import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.Feature
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoService(
    private val photoRepository: PhotoRepository,
    private val storageComponent: StorageComponent,
    private val cloudVisionTemplate: CloudVisionTemplate
) {

    fun create(description: String, file: MultipartFile) =
        extractLabels(file, 5).let { storePhoto(file, description, it) }

    fun findById(id: String): Photo? = photoRepository.findById(id).orElse(null)

    fun downloadUrlById(id: String) = findById(id)
        ?.let { storageComponent.getDownloadUrl(it.filePath) }
        ?: throw RuntimeException("Photo não encontrada.")

    fun findAllByLabel(label: String) = photoRepository.findAllByLabels(label)

    private fun storePhoto(file: MultipartFile, description: String, labels: List<String>) =
        storageComponent
            .upload(file)
            ?.let { filePath -> Photo(description = description, filePath = filePath, labels = labels) }
            .let { photoRepository.save(it) }

    private fun extractLabels(file: MultipartFile, numberOfLabels: Int): List<String> {
        val labels = cloudVisionTemplate
            .analyzeImage(file.resource, Feature.Type.LABEL_DETECTION)
            .labelAnnotationsList
            .take(numberOfLabels)
            .map { it.description }
        return labels
    }
}