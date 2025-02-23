/*
 * Copyright 2018 Google LLC
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

package com.blanc08.sid.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.JacksonSerializer
import io.github.jan.supabase.storage.Storage


abstract class AppSupabase {
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: SupabaseClient? = null

        fun getInstance(): SupabaseClient {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase().also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(): SupabaseClient {
            return createSupabaseClient(
                supabaseUrl = "https://vjkltshyvzrjxiwhgoln.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZqa2x0c2h5dnpyanhpd2hnb2xuIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyODIzNzY1MywiZXhwIjoyMDQzODEzNjUzfQ.2HtJEmdIdPjGZKauJGfQeve2wyKoonGTxwYRLHb14tE"
            ) {
                install(Auth)
                install(Postgrest)
                install(Storage)
                defaultSerializer = JacksonSerializer()
            }
        }

        // fun providePlaceDao() {
        //     return
        // }
    }
}
