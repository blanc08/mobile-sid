package com.blanc08.sid.feature.gallery.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blanc08.sid.feature.gallery.GalleryRoute

const val PICTURE_ID_ARG = "pictureId"
const val GALLERY_ROUTE = "gallery"

fun NavController.navigateToGallery(navOptions: NavOptions) {
    navigate(GALLERY_ROUTE, navOptions)
}

fun NavController.navigateToPicture(pictureId: String, navOptions: NavOptions? = null) {
    navigate(GALLERY_ROUTE, navOptions)
}

fun NavGraphBuilder.galleryScreen(onPictureClick: (String) -> Unit) {
    composable(
        route = GALLERY_ROUTE,
    ) {
        GalleryRoute(onPictureClick)
    }
}
