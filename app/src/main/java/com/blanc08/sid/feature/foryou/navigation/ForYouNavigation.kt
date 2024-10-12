package com.blanc08.sid.feature.foryou.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.blanc08.sid.feature.foryou.ForYouRoute

const val FOR_YOU_ROUTE = "FOR_YOU"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(onCardClick: (String) -> Unit) {
    composable(route = FOR_YOU_ROUTE) {
        ForYouRoute(
            onCardClick = onCardClick,
        )
    }
}
