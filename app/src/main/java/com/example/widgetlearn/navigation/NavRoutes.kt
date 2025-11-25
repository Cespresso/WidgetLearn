package com.example.widgetlearn.navigation

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object NoteDetail : NavRoutes("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
}
