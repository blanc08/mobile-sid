package com.blanc08.sid.data.place

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import java.util.UUID
import javax.inject.Inject


class PlaceRepository @Inject constructor(private val client: SupabaseClient) {

    suspend fun getPlaces(delta: String = ""): List<Place> {
        return client.from("place").select {
            filter {
                if (delta.isNotEmpty()) {
                    gt("created_at", delta)
                }
            }
        }.decodeList<Place>()
    }

    suspend fun getPlace(id: String) = client.from("place").select {
        filter {
            eq("id", id)
        }
    }.decodeSingle<Place>()

    suspend fun uploadFile(byteArray: ByteArray, fileExtension: String = ".jpg"): String {
        try {
            val path = UUID.randomUUID().toString() + fileExtension
            val bucket = client.storage.from("places")
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

    suspend fun createOne(newPlace: NewPlaceDao): Place {
        return client.from("place").insert(newPlace) {
            select()
        }.decodeSingle<Place>()
    }

    companion object {
        private const val TAG = "PlaceRepository"
    }
}
