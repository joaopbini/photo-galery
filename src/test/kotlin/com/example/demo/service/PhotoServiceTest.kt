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
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.multipart.MultipartFile
import java.util.*

class PhotoServiceTest {

    private val photoRepository: PhotoRepository = mockk()
    private val storageComponent: StorageComponent = mockk()
    private val cloudVisionTemplate: CloudVisionTemplate = mockk()
    private val multipartFile: MultipartFile = mockk(relaxed = true)
    private val annotateImageResponse: AnnotateImageResponse = mockk()

    private val photoService = PhotoService(photoRepository, storageComponent, cloudVisionTemplate)

    @Test
    fun createTest() {

        val photoSlot = slot<Photo>()
        every { storageComponent.upload(multipartFile) } returns "id"
        every { cloudVisionTemplate.analyzeImage(any(), any<Feature.Type>()) } returns annotateImageResponse
        every { annotateImageResponse.labelAnnotationsList } returns listOf(
            EntityAnnotation.newBuilder().setDescription("teste").build()
        )
        every { photoRepository.save(capture(photoSlot)) } returns Photo()

        photoService.create("description", multipartFile)

        assertEquals("id", photoSlot.captured.filePath)
    }

    @Test
    fun findByIdTest() {

        every { photoRepository.findById(any()) } returns Optional.ofNullable(null)

        photoService.findById("id")

        verify(exactly = 1) { photoRepository.findById(any()) }
    }

    @Test
    fun downloadUrlByIdTest() {

        every { photoRepository.findById(any()) } returns Optional.of(Photo())
        every { storageComponent.getDownloadUrl(any()) } returns "url"

        val response = photoService.downloadUrlById("id")

        assertEquals("url", response)
    }

    @Test
    fun downloadUrlByIdNotFoundTest() {

        every { photoRepository.findById(any()) } returns Optional.ofNullable(null)

        assertThrows<RuntimeException> { photoService.downloadUrlById("id") }
    }

    @Test
    fun findAllByLabelTest() {

        every { photoRepository.findAllByLabels(any()) } returns listOf()

        val response = photoService.findAllByLabel("label")

        assertTrue(response.isEmpty())
    }
}