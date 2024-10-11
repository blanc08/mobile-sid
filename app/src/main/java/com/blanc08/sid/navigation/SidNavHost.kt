package com.blanc08.sid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.blanc08.sid.compose.NiaAppState
import com.blanc08.sid.feature.foryou.navigation.FOR_YOU_ROUTE
import com.blanc08.sid.feature.foryou.navigation.forYouScreen
import com.blanc08.sid.feature.place.navigation.navigateToPlaceDetail


/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun SidNavHost(
    appState: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = FOR_YOU_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        forYouScreen(onCardClick = navController::navigateToPlaceDetail)
        // gallerySccreen(
        //     onPictureClick = navController::navigateToInterests,
        //     onShowSnackbar = onShowSnackbar,
        // )
        // searchScreen(
        //     onBackClick = navController::popBackStack,
        //     onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
        //     onTopicClick = navController::navigateToInterests,
        // )
        // interestsListDetailScreen()
    }
}
