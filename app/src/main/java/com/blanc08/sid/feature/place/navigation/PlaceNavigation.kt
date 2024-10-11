package com.blanc08.sid.feature.place.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blanc08.sid.feature.place.PlaceDetailRoute

const val PLACE_ID_ARG = "placeId"
const val PLACE_ROUTE_BASE = "place_route"
const val PLACE_ROUTE = "$PLACE_ROUTE_BASE?$PLACE_ID_ARG={$PLACE_ID_ARG}"


fun NavController.navigateToPlaceDetail(placeId: String? = null, navOptions: NavOptions? = null) {
    val route = if (placeId != null) {
        "${PLACE_ROUTE_BASE}?${PLACE_ID_ARG}=$placeId"
    } else {
        PLACE_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.placeScreen(onBackClick: () -> Unit) {
    composable(
        route = PLACE_ROUTE,
        arguments = listOf(
            navArgument(PLACE_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        PlaceDetailRoute(onBackClick = onBackClick)
    }
}
