package com.example.demo.components

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.TimeUnit

@Component
class StorageComponent(private val storage: Storage) {

    companion object {

        @Value("\${gcp.storage.bucket.name}")
        const val BUCKET_NAME = "gcp.storage.bucket.name unset"

        private val allowedContentType = listOf(
            ContentType.IMAGE_JPEG.mimeType,
            ContentType.IMAGE_PNG.mimeType,
            ContentType.IMAGE_SVG.mimeType,
        )
    }

    fun upload(file: MultipartFile) =
        file
            .takeIf { file.contentType in allowedContentType }
            ?.let { buildBlobInfo(file) }
            ?.let { storage.create(it, file.bytes) }
            ?.blobId
            ?.name
            ?: error("Content type ${file.contentType} not allowed!")

    fun getDownloadUrl(filename: String)=
        BlobInfo.newBuilder(BUCKET_NAME, filename).build()
            .let { storage.signUrl(it, 60, TimeUnit.HOURS)}
            .toString()

    private fun buildBlobInfo(file: MultipartFile) =
        BlobId.of(BUCKET_NAME, file.originalFilename)
            .let(BlobInfo::newBuilder)
            .setContentType(file.contentType)
            .build()
}