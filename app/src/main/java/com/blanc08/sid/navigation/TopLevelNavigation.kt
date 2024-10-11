package com.blanc08.sid.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.blanc08.sid.R
import com.blanc08.sid.designsystem.icon.SidIcons


/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    FOR_YOU(
        selectedIcon = SidIcons.Upcoming,
        unselectedIcon = SidIcons.UpcomingBorder,
        iconTextId = R.string.feature_foryou_title,
        titleTextId = R.string.app_name,
    ),
    GALLERY(
        selectedIcon = SidIcons.Bookmarks,
        unselectedIcon = SidIcons.BookmarksBorder,
        iconTextId = R.string.gallery_title,
        titleTextId = R.string.gallery_title,
    ),
    ABOUT(
        selectedIcon = SidIcons.Grid3x3,
        unselectedIcon = SidIcons.Grid3x3,
        iconTextId = R.string.about_title,
        titleTextId = R.string.about_title,
    ),
}
