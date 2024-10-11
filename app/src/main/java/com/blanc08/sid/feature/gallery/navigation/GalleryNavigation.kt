package com.blanc08.sid.feature.gallery.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blanc08.sid.feature.gallery.GalleryRoute
import com.blanc08.sid.feature.gallery.NewPhotoScreen
import com.blanc08.sid.feature.gallery.PhotoDetailRoute

const val PHOTO_ID_ARG = "pictureId"
const val GALLERY_ROUTE = "gallery"
const val NEW_PLACE_FORM_ROUTE = "new_photo"
const val GALLERY_DETAIL_ROUTE = "$GALLERY_ROUTE?$PHOTO_ID_ARG={$PHOTO_ID_ARG}"

fun NavController.navigateToGallery(navOptions: NavOptions) {
    navigate(GALLERY_ROUTE, navOptions)
}

fun NavController.navigateToPicture(pictureId: String? = null, navOptions: NavOptions? = null) {
    Log.d("Gallery Navigation", pictureId.toString())
    val route = if (pictureId != null) {
        "${GALLERY_ROUTE}?${PHOTO_ID_ARG}=$pictureId"
    } else {
        GALLERY_ROUTE
    }
    navigate(route, navOptions)
}


fun NavGraphBuilder.galleryScreen(
    onPictureClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = GALLERY_ROUTE,
    ) {
        GalleryRoute(onPictureClick)
    }

    composable(
        route = GALLERY_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(PHOTO_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        PhotoDetailRoute(onBackClick)
    }

    composable(route = NEW_PLACE_FORM_ROUTE) {
        NewPhotoScreen(onBackClick = onBackClick)
    }
}
