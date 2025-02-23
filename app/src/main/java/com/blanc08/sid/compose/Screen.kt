package com.blanc08.sid.compose

/*
 * Copyright 2023 Google LLC
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

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen("home")

    data object Transaction : Screen(route = "transaction")

    data object Report : Screen(route = "profile")

    data object NewPlace : Screen(route = "NewPlace")


    data object PlaceDetail : Screen(
        route = "place/{placeId}",
        navArguments = listOf(navArgument("placeId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(placeId: String) = "place/${placeId}"
    }
}

