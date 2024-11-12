package com.example.proyectonotas.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectonotas.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDatabaseDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note>

    @Insert(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}