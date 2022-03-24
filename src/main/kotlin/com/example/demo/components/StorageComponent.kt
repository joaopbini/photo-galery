package com.example.demo.components

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.apache.http.entity.ContentType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.TimeUnit

@Component
class StorageComponent(private val storage: Storage) {

    private val bucketName = "tdc-bucket"

    private val allowedContentType =
        listOf(
            ContentType.IMAGE_JPEG.mimeType,
            ContentType.IMAGE_PNG.mimeType,
            ContentType.IMAGE_SVG.mimeType
        )

    fun upload(file: MultipartFile): String? {

        val contentType = file.contentType
        val allowedType = allowedContentType.contains(contentType)

        if (allowedType.not()) error("Content type $contentType not allowed!")

        return storage.create(buildBlobInfo(file), file.bytes).blobId.name
    }

    fun getDownloadUrl(filename: String): String {
        return storage.get("tdc-bucket", filename)
            .signUrl(60, TimeUnit.HOURS, Storage.SignUrlOption.withV4Signature())
            .toString()
    }

    private fun buildBlobInfo(file: MultipartFile): BlobInfo? {
        val blobId = BlobId.of(bucketName, file.originalFilename)
        return BlobInfo.newBuilder(blobId).setContentType(file.contentType).build()
    }


}