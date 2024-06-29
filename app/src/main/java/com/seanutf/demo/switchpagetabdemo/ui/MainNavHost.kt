package com.seanutf.demo.switchpagetabdemo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seanutf.demo.switchpagetabdemo.ui.home.HomeScreen
import com.seanutf.demo.switchpagetabdemo.ui.profile.ProfileScreen
import com.seanutf.demo.switchpagetabdemo.ui.search.SearchScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavItem.Home.route,
    ) {
        composable(
            route = BottomNavItem.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = BottomNavItem.Search.route
        ) {
            SearchScreen()
        }

        composable(
            route = BottomNavItem.Search.route
        ) {
            SearchScreen()
        }

        composable(
            route = BottomNavItem.Profile.route
        ) {
            ProfileScreen()
        }
    }
}

internal fun navigateScreen(navController: NavHostController, navItem: BottomNavItem) {
    navController.navigate(navItem.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

internal val mainScreenList = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Profile)