package com.example.proyectonotas.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.compose.backgroundDark
import com.example.proyectonotas.R
import com.example.proyectonotas.dialogs.CancelDialog
import com.example.proyectonotas.forms.NoteForm
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.AddNoteForm
import com.example.proyectonotas.navigation.Home
import com.example.proyectonotas.navigation.IndividualNote
import com.example.proyectonotas.navigation.NotesList
import com.example.proyectonotas.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteFormView(viewModel: NoteViewModel, navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(text = "Agregar Nota", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
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

        // para el toast
        var context = LocalContext.current
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var backgroundColor by remember { mutableStateOf("") }

        var showCancelDialog by remember { mutableStateOf(false) }

        NoteForm(
            title = title,
            onTitleChange = { newTitle -> title = newTitle },
            content = content,
            onContentChange = { newContent -> content = newContent },
            backgroundColor = backgroundColor,
            onBackgroundColorChange = { newBackgroundColor -> backgroundColor = newBackgroundColor },
            primaryButtonText = "Crear nota",
            primaryButtonIconId = R.drawable.icono_agregar,
            primaryButtonAction = {
                viewModel.addNote(Note(title = title, content = content, backgroundColor = backgroundColor))
                navController.navigate(NotesList)
                Toast.makeText(context, "Nota creada exitosamente", Toast.LENGTH_SHORT).show()
            },
            secondaryButtonText = "Cancelar",
            secondaryButtonIconId = R.drawable.icono_cancelar,
            secondaryButtonColor = MaterialTheme.colorScheme.secondary,
            secondaryButtonAction = {
                showCancelDialog = true
            },
            modifier = Modifier.padding(innerPadding))

        if (showCancelDialog) {
            CancelDialog(
                onDismissRequest = {
                    showCancelDialog = false
                },
                onConfirmation = {
                    navController.navigate(NotesList)
                }
            )
        }
    }
}


