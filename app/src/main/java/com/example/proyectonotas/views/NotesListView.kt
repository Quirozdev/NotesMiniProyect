package com.example.proyectonotas.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.proyectonotas.R
import com.example.proyectonotas.dialogs.SimpleDialog
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.AddNoteForm
import com.example.proyectonotas.navigation.EditNoteForm
import com.example.proyectonotas.navigation.Home
import com.example.proyectonotas.navigation.NotesList
import com.example.proyectonotas.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListView(viewModel: NoteViewModel, navController: NavController, modifier: Modifier = Modifier) {
    // para el toast
    var context = LocalContext.current
    var noteToDelete: Note by remember { mutableStateOf(Note()) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(text = "Mis Notas", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton (onClick = {
                        navController.navigate(Home)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddNoteForm)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(imageVector = Icons.Filled.Add, "Agregar producto")
            }
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding).fillMaxSize()) {
            // Estado del viewModel.
            val estado = viewModel.estado

            // Carga.

            if (estado.estaCargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                // si no se han agregado notas
            } else if (estado.notes.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().padding(24.dp)) {

                    Text(text = "¡No hay notas por mostrar, trata de agregar algunas!", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                }
            } else {
                LazyColumn(modifier = Modifier
                    .padding(16.dp)
                    , verticalArrangement = Arrangement.spacedBy(24.dp)) {

                    // Definición de los registros.
                    items(estado.notes) {

                        val backgroundColor = if (it.backgroundColor.isNotBlank()) Color(it.backgroundColor.toColorInt())  else MaterialTheme.colorScheme.surfaceVariant
                        val textColor = if (it.textColor.isNotBlank()) Color(it.textColor.toColorInt()) else MaterialTheme.colorScheme.onSurfaceVariant

                        ElevatedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = backgroundColor),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal =  20.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = textColor
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    SmallFloatingActionButton(
                                        onClick = {
                                            navController.navigate(EditNoteForm(noteId = it.id))
                                        },
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.onSurface,
                                    ) {
                                        Icon(Icons.Filled.Edit, "Editar nota")
                                    }
                                    SmallFloatingActionButton(
                                        onClick = {
                                            showDeleteDialog = true
                                            noteToDelete = it
                                        },
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                                    ) {
                                        Icon(Icons.Filled.Delete, "Eliminar nota")
                                    }
                                }
                            }
                            HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                text = it.content,
                                color = textColor,
                                modifier = Modifier.padding(24.dp)
                            )
                        }
                    }
                }
            }
            if (showDeleteDialog) {
                OpenDeleteDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        noteToDelete = Note()
                    },
                    onConfirmation = {
                        try {
                            viewModel.deleteNote(noteToDelete)
                            Toast.makeText(context, "Nota eliminada exitosamente", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            println(e)
                        } finally {
                            showDeleteDialog = false
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun OpenDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    SimpleDialog(
        onDismissRequest = { onDismissRequest() },
        onConfirmation = { onConfirmation() },
        dialogTitle = "¿Borrar nota?",
        dialogText = "Esta acción es irreversible"
    )
}