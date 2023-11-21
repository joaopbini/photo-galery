package com.example.demo.photo

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoRepository : DatastoreRepository<Photo, String> {

    fun findAllByLabels(label: String): List<Photo>
}