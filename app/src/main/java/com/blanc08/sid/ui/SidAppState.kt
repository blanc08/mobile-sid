package com.blanc08.sid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.blanc08.sid.feature.about.navigation.navigateToAbout
import com.blanc08.sid.feature.foryou.navigation.FOR_YOU_ROUTE
import com.blanc08.sid.feature.foryou.navigation.navigateToForYou
import com.blanc08.sid.feature.gallery.navigation.GALLERY_ROUTE
import com.blanc08.sid.feature.gallery.navigation.navigateToGallery
import com.blanc08.sid.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberSidAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SidAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
    ) {
        SidAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}


@Stable
class SidAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            FOR_YOU_ROUTE -> TopLevelDestination.FOR_YOU
            GALLERY_ROUTE -> TopLevelDestination.GALLERY
            // ABOUT_ROUTE -> TopLevelDestination.ABOUT
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when re-selecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
                TopLevelDestination.GALLERY -> navController.navigateToGallery(topLevelNavOptions)
                TopLevelDestination.ABOUT -> navController.navigateToAbout(topLevelNavOptions)
            }
        }
    }

    // fun navigateToSearch() = navController.navigateToSearch()
}

/**
 * Stores information about navigation events to be used with JankStats
 */
// @Composable
// private fun NavigationTrackingSideEffect(navController: NavHostController) {
//     TrackDisposableJank(navController) { metricsHolder ->
//         val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//             metricsHolder.state?.putState("Navigation", destination.route.toString())
//         }
//
//         navController.addOnDestinationChangedListener(listener)
//
//         onDispose {
//             navController.removeOnDestinationChangedListener(listener)
//         }
//     }
// }
