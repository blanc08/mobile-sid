package com.blanc08.sid.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.ui.graphics.vector.ImageVector
import com.blanc08.sid.compose.Screen

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {

    // function to get the list of bottomNavigationItems
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screen.Home.route
            ),
            BottomNavigationItem(
                label = "Transaction",
                icon = Icons.Rounded.MonitorHeart,
                route = Screen.Transaction.route
            ),
            BottomNavigationItem(
                label = "Report",
                icon = Icons.Filled.Bookmark,
                route = Screen.Report.route
            ),
        )
    }
}
