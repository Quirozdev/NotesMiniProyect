package com.example.proyectonotas.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object NotesList

@Serializable
object AddNoteForm

@Serializable
data class EditNoteForm(val noteId: Int)