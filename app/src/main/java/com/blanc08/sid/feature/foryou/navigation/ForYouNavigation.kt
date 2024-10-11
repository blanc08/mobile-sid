package com.blanc08.sid.feature.foryou.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.blanc08.sid.feature.foryou.ForYouRoute

const val FOR_YOU_ROUTE = "for_you_route"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(onCardClick: (String) -> Unit) {
    composable(
        route = FOR_YOU_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        ForYouRoute(
            onCardClick = onCardClick,
        )
    }
}
