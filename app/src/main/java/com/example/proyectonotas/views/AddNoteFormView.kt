package com.example.proyectonotas.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectonotas.R
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.AddNoteForm
import com.example.proyectonotas.navigation.Home
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
        Formulario(viewModel = viewModel, navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Formulario(viewModel: NoteViewModel, navController: NavController, modifier: Modifier) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically), modifier = modifier.fillMaxSize()) {
        Field(label = "Titulo", value = title, onValueChange = { title = it }, errorMessage = "")
        Field(label = "Contenido", value = content, onValueChange = { content = it }, errorMessage = "", textArea = true)
        ExtendedFloatingActionButton(
            text = {
                Text(text = "Agregar Nota", style = MaterialTheme.typography.titleMedium)
            },
            onClick = {
                viewModel.addNote(Note(title = title, content = content))
                navController.navigate(NotesList)
            },
            icon = { Image(painter = painterResource(id = R.drawable.icono_agregar), contentDescription = "Agregar Nota", modifier = Modifier.size(28.dp)) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.width(300.dp).height(70.dp)
        )
    }
}

@Composable
fun Field(label: String, value: String, onValueChange: (String) -> Unit, errorMessage: String, textArea: Boolean = false) {
    TextField(value = value, label = { Text(label) }, onValueChange = onValueChange, modifier = if (textArea) Modifier.height(320.dp).width(320.dp) else Modifier)
}