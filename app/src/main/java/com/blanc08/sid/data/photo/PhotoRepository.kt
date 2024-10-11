package com.blanc08.sid.data.photo

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import java.util.UUID
import javax.inject.Inject


class PhotoRepository @Inject constructor(private val client: SupabaseClient) {

    suspend fun getPhotos(delta: String = ""): List<Photo> {
        try {
            val response = client.from("photo").select {
                filter {
                    if (delta.isNotEmpty()) {
                        gt("created_at", delta)
                    }
                }
            }.decodeList<Photo>()

            return response
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()

    }

    suspend fun getPhoto(id: String) = client.from("photo").select {
        filter {
            eq("id", id)
        }
    }.decodeSingle<Photo>()

    suspend fun uploadFile(byteArray: ByteArray, fileExtension: String = ".jpg"): String {
        try {
            val path = UUID.randomUUID().toString() + fileExtension
            val bucket = client.storage.from("photos")
            val response = bucket.upload(path, byteArray) {
                upsert = false
            }

            Log.d(TAG, response.path)
            val url = bucket.publicUrl(path)
            return url
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    suspend fun createOne(record: NewPhoto): Photo {
        return client.from("photo").insert(record) {
            select()
        }.decodeSingle<Photo>()
    }

    companion object {
        private const val TAG = "PlaceRepository"
    }
}
