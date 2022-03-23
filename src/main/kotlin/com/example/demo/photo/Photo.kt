package com.example.demo.photo

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import org.springframework.data.annotation.Id
import java.util.*

@Entity
data class Photo(

    @Id
    val id: String = UUID.randomUUID().toString(),

    val description: String = "",

    val filePath: String = "",

    val labels: List<String> = listOf()

)
