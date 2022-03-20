package com.example.demo.photo

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/photo")
class PhotoApi(private val photoService: PhotoService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = photoService.findById(id)

    @PostMapping("/create", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(@RequestParam("file") file: MultipartFile): Photo {
        return photoService.create(file)
    }

    @GetMapping("/download-url")
    fun urlDownload(@RequestParam filePath: String) = photoService.downloadUrlById(filePath)

    @GetMapping("/find/label")
    fun findByLabel(@RequestParam label: String) = photoService.findAllByLabel(label)


}