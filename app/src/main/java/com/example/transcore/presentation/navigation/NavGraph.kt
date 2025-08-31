package com.example.transcore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.transcore.presentation.ui.history.view.HistoryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onHistoryClick = { navController.navigate("history") }
            )
        }
        composable("history") {
            HistoryScreen(onBackClick = { navController.popBackStack() })
        }
    }

}