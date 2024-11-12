package com.example.proyectonotas.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.room.NotesDatabaseDao
import com.example.proyectonotas.state.NoteState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NotesDatabaseDao
): ViewModel() {

    // Estado del modelo.
    var estado by mutableStateOf(NoteState())
        private set

    // Inicializar del view model.
    init {
        viewModelScope.launch {
            estado = estado.copy(
                estaCargando = true
            )
            // Obtener la lista de notas en la base de datos.
            dao.getNotes().collectLatest {
                estado = estado.copy(
                    notes = it,
                    estaCargando = false
                )
            }
        }
    }

    fun getNoteById(id: Int): Note? {
        return estado.notes.find {
            id == it.id
        }
    }

    fun addNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteNote(note)
        }
    }
}