package com.example.proyectonotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.compose.ProyectoNotasTheme
import com.example.proyectonotas.navigation.NavManager
import com.example.proyectonotas.room.NoteDatabase
import com.example.proyectonotas.viewmodels.NoteViewModel
import com.example.proyectonotas.views.HomeView

class MainActivity : ComponentActivity() {

    // inicializar la base de datos
    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = NoteDatabase::class.java,
            name = "notes.db"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Instancia del viewModel con el DAO.
        val viewModel: NoteViewModel = NoteViewModel(db.build().notesDao())

        setContent {
            ProyectoNotasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavManager(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}