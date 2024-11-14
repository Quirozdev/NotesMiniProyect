package com.example.proyectonotas.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.proyectonotas.R
import com.example.proyectonotas.dialogs.OpenDeleteDialog
import com.example.proyectonotas.forms.NoteForm
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.AddNoteForm
import com.example.proyectonotas.navigation.EditNoteForm
import com.example.proyectonotas.navigation.Home
import com.example.proyectonotas.navigation.NotesList
import com.example.proyectonotas.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(noteId: Int, navController: NavController, viewModel: NoteViewModel, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(text = "Información de la nota", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton (onClick = {
                        navController.navigate(NotesList)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                        )
                    }
                },
            )
        }
    ) { innerPadding ->

        val currentNote = viewModel.getNoteById(noteId) ?: Note()

        var context = LocalContext.current
        var showDeleteDialog by remember { mutableStateOf(false) }

        NoteForm(
            title = currentNote.title,
            onTitleChange = {  },
            content = currentNote.content,
            onContentChange = {  },
            backgroundColor = currentNote.backgroundColor,
            onBackgroundColorChange = { },
            primaryButtonText = "Editar",
            primaryButtonIconId = R.drawable.icono_editar,
            primaryButtonColor = MaterialTheme.colorScheme.tertiary,
            primaryButtonAction = {
                navController.navigate(EditNoteForm(noteId = currentNote.id))
            },
            secondaryButtonText = "Borrar",
            secondaryButtonIconId = R.drawable.icono_borrar,
            secondaryButtonColor = MaterialTheme.colorScheme.error,
            secondaryButtonAction = {
                showDeleteDialog = true
            },
            disableAllFields = true,
            modifier = Modifier.padding(innerPadding))

        if (showDeleteDialog) {
            OpenDeleteDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                },
                onConfirmation = {
                    try {
                        viewModel.deleteNote(currentNote)
                    } catch (e: Exception) {
                        println(e)
                    } finally {
                        showDeleteDialog = false
                        Toast.makeText(context, "Nota eliminada exitosamente", Toast.LENGTH_SHORT).show()
                        navController.navigate(NotesList)
                    }
                }
            )
        }
    }
}