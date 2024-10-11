/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blanc08.sid.data.photo

import android.util.Log
import com.blanc08.sid.data.place.Photo
import com.blanc08.sid.data.place.NewPlace
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.Serializable
import java.util.UUID
import javax.inject.Inject

@Serializable
data class NewGallery(
    val name: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val image: String? = "",
) {
    override fun toString() = name
}

class PhotoRepository @Inject constructor(private val client: SupabaseClient) {

    suspend fun getPlaces(delta: String = ""): List<Photo> {
        return client.from("place").select {
            filter {
                if (delta.isNotEmpty()) {
                    gt("created_at", delta)
                }
            }
        }.decodeList<Photo>()
    }

    suspend fun getPlace(id: String) = client.from("place").select {
        filter {
            eq("id", id)
        }
    }.decodeSingle<Photo>()

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

    suspend fun createOne(newPlace: NewPlace): Photo {
        return client.from("place").insert(newPlace) {
            select()
        }.decodeSingle<Photo>()
    }

    companion object {
        private const val TAG = "PlaceRepository"
    }
}
