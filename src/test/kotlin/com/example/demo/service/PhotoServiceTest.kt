package com.example.demo.service

import com.example.demo.components.StorageComponent
import com.example.demo.photo.Photo
import com.example.demo.photo.PhotoRepository
import com.example.demo.photo.PhotoService
import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.EntityAnnotation
import com.google.cloud.vision.v1.Feature
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.web.multipart.MultipartFile

class PhotoServiceTest {

    private val photoRepository: PhotoRepository = mockk()
    private val storageComponent: StorageComponent = mockk()
    private val cloudVisionTemplate: CloudVisionTemplate = mockk()

    private val photoService = PhotoService(photoRepository, storageComponent, cloudVisionTemplate)

    private val multipartFile: MultipartFile = mockk(relaxed = true)

    private val annotateImageResponse: AnnotateImageResponse = mockk()

    @Test
    fun createTest() {

        val photoSlot = slot<Photo>()

        every { storageComponent.upload(multipartFile) } returns "id"

        every { cloudVisionTemplate.analyzeImage(any(), any<Feature.Type>()) } returns annotateImageResponse

        every { annotateImageResponse.labelAnnotationsList } returns listOf(
            EntityAnnotation.newBuilder()
                .setDescription("teste").build()
        )

        every { photoRepository.save(capture(photoSlot)) } returns Photo()

        photoService.create(multipartFile)

        assertEquals("id", photoSlot.captured.filePath)

    }


}