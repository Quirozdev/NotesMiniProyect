package com.example.proyectonotas.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectonotas.R
import com.example.proyectonotas.forms.NoteForm
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.NotesList
import com.example.proyectonotas.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteFormView(noteId: Int, navController: NavController, viewModel: NoteViewModel, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(text = "Editar Nota", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
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

        val noteToEdit = viewModel.getNoteById(noteId) ?: Note()
        // para el toast
        var context = LocalContext.current
        var title by remember { mutableStateOf(noteToEdit.title) }
        var content by remember { mutableStateOf(noteToEdit.content) }
        var backgroundColor by remember { mutableStateOf(noteToEdit.backgroundColor) }
        var textColor by remember { mutableStateOf(noteToEdit.textColor) }


        NoteForm(
            title = title,
            onTitleChange = { newTitle -> title = newTitle },
            content = content,
            onContentChange = { newContent -> content = newContent },
            backgroundColor = backgroundColor,
            onBackgroundColorChange = { newBackgroundColor -> backgroundColor = newBackgroundColor },
            textColor = textColor,
            onTextColorChange = { newTextColor -> textColor = newTextColor },
            submitButtonText = "Actualizar Nota",
            submitButtonIconId = R.drawable.icono_editar,
            onSubmit = {
                viewModel.updateNote(Note(id = noteId, title = title, content = content, backgroundColor = backgroundColor, textColor = textColor))
                navController.navigate(NotesList)
                Toast.makeText(context, "Nota actualizada exitosamente", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(innerPadding))
    }
}