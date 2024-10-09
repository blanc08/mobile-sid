package com.blanc08.sid.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blanc08.sid.compose.home.HomeScreen
import com.blanc08.sid.R
import com.blanc08.sid.compose.components.BottomNavigationItem
import com.blanc08.sid.compose.place.PlaceDetailsScreen
import com.blanc08.sid.ui.theme.AppTheme

@Composable
fun SIDApp() {
    SakuHost()
}

@Composable
fun SakuHost() {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomBar(
                navigationSelectedItem = navigationSelectedItem,
                onNavBarClick = { navigationItem, index ->
                    navigationSelectedItem = index

                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onCardClick = {
                        navController.navigate(
                            Screen.PlaceDetail.createRoute(
                                id = it.id
                            )
                        )
                    }
                )
            }

            composable(
                route = Screen.PlaceDetail.route,
                arguments = Screen.PlaceDetail.navArguments
            ) {
                PlaceDetailsScreen(
                    onBackClick = { navController.navigateUp() },
                )
            }

            composable(Screen.Transaction.route) {
                Text("Transaction list")
//                TransactionScreen(
//                    onPhotoClick = {
//                        val uri = Uri.parse(it.user.attributionUrl)
//                        val intent = Intent(Intent.ACTION_VIEW, uri)
//                        activity.startActivity(intent)
//                    },
//                    onUpClick = {
//                        navController.navigateUp()
//                    })
            }
            composable(Screen.Report.route) {
                Text("Report page")
            }
        }
    }
}

@Composable
fun BottomBar(
    navigationSelectedItem: Int = 0,
    onNavBarClick: (navigationItem: BottomNavigationItem, index: Int) -> Unit
) {
    NavigationBar()
    {
        BottomNavigationItem().bottomNavigationItems()
            .forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(navigationItem.label)
                    },
                    icon = {
                        Icon(
                            navigationItem.icon,
                            contentDescription = navigationItem.label
                        )
                    },
                    onClick = {
                        onNavBarClick(navigationItem, index)
                    }
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("TopAppBar"),
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun RiceClassifierAppPreview() {
    AppTheme {
        SIDApp()
    }
}