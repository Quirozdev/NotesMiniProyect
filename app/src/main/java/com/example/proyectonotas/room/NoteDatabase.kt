package com.example.proyectonotas.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectonotas.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDatabaseDao
}