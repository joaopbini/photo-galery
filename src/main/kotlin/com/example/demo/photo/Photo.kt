package com.example.demo.photo

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import org.springframework.data.annotation.Id
import java.util.UUID

@Entity
data class Photo(

    @Id
    var id: String = UUID.randomUUID().toString(),

    var labels: List<String> = listOf(),

    var filePath: String = ""

)
