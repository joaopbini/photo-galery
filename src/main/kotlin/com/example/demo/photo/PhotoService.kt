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

    fun create(file: MultipartFile): Photo {

        val fileId = storageComponent.upload(file) ?: ""

        val visionResponse = cloudVisionTemplate.analyzeImage(file.resource, Feature.Type.LABEL_DETECTION)

        val labels = visionResponse.labelAnnotationsList.take(5).map { it.description }

        return photoRepository.save(Photo( filePath = fileId, labels = labels))
    }

    fun findById(id: String): Photo? = photoRepository.findById(id).orElse(null)

    fun downloadUrlById(id: String): String {
        val photo = findById(id) ?: throw RuntimeException("Photo não encontrada.")
        return storageComponent.getDownloadUrl(photo.filePath)
    }

    fun findAllByLabel(categoria: String): List<Photo> {
        return photoRepository.findAllByLabels(categoria)
    }

}