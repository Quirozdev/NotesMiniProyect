package com.example.proyectonotas.state

import com.example.proyectonotas.model.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val estaCargando: Boolean = false
)
