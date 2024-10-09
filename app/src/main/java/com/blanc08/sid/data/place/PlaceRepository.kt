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

package com.blanc08.sid.data.place

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val client: SupabaseClient) {

    suspend fun getPlaces(delta: String = ""): List<Place> {
        return client.from("place").select() {
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

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}
