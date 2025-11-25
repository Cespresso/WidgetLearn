package com.example.widgetlearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.widgetlearn.navigation.NavRoutes
import com.example.widgetlearn.ui.home.HomeScreen
import com.example.widgetlearn.ui.notedetail.NoteDetailScreen
import com.example.widgetlearn.ui.theme.WidgetLearnTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WidgetLearnTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.Home.route
                ) {
                    composable(route = NavRoutes.Home.route) {
                        HomeScreen(
                            onNoteClick = { noteId ->
                                navController.navigate(NavRoutes.NoteDetail.createRoute(noteId))
                            }
                        )
                    }
                    composable(
                        route = NavRoutes.NoteDetail.route,
                        arguments = listOf(
                            navArgument("noteId") { type = NavType.LongType }
                        )
                    ) {
                        NoteDetailScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
