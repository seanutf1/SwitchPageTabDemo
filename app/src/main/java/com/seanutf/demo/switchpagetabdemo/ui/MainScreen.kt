package com.seanutf.demo.switchpagetabdemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.seanutf.demo.switchpagetabdemo.ui.theme.SwitchPageTabDemoTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar{ navItem ->
                navigateScreen(navController, navItem)
            }
        }
    ) { innerPadding ->
        MainNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}


@Composable
fun BottomNavigationBar(
    navigateToTopLevelDestination: (navItem: BottomNavItem) -> Unit,
) {

    val selectedDestination = remember {
        BottomNavItem.Home.route
    }
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        mainScreenList.forEach { replyDestination ->
            NavigationBarItem(
                selected = selectedDestination == replyDestination.route,
                onClick = {
                    navigateToTopLevelDestination(replyDestination)
                },
                icon = {
                    Icon(
                        imageVector = replyDestination.icon,
                        contentDescription = replyDestination.label
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SwitchPageTabDemoTheme {
        MainScreen()
    }
}