package com.example.proyectonotas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.proyectonotas.viewmodels.NoteViewModel
import com.example.proyectonotas.views.AddNoteFormView
import com.example.proyectonotas.views.EditNoteFormView
import com.example.proyectonotas.views.HomeView
import com.example.proyectonotas.views.NoteView
import com.example.proyectonotas.views.NotesListView

@Composable
fun NavManager(viewModel: NoteViewModel, modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeView(navController, modifier)
        }
        composable<NotesList> {
            NotesListView(viewModel, navController, modifier)
        }
        composable<IndividualNote> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<IndividualNote>()
            NoteView(args.noteId, navController, viewModel)
        }
        composable<AddNoteForm> {
            AddNoteFormView(viewModel, navController, modifier)
        }
        composable<EditNoteForm> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<EditNoteForm>()
            EditNoteFormView(args.noteId, navController, viewModel)
        }
    }
}