package com.example.proyectonotas.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object NotesList
@Serializable
data class IndividualNote(val noteId: Int)

@Serializable
object AddNoteForm

@Serializable
data class EditNoteForm(val noteId: Int)