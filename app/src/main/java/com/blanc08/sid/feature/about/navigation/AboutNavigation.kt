package com.blanc08.sid.feature.about.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.blanc08.sid.feature.about.AboutRoute

const val ABOUT_ROUTE = "about_route"

fun NavController.navigateToAbout(navOptions: NavOptions) = navigate(ABOUT_ROUTE, navOptions)

fun NavGraphBuilder.aboutScreen() {
    composable(route = ABOUT_ROUTE) { AboutRoute() }
}

