package com.example.mynotes.database

import androidx.lifecycle.LiveData
import com.example.mynotes.models.AppNote

interface DatabaseRepository {
    val allNotes:LiveData<List<AppNote>>
    suspend fun insert(note: AppNote,onSuccess:()->Unit)
    suspend fun delete(note: AppNote,onSuccess:()->Unit)

    fun connectToDatabase(onSuccess: () -> Unit, onFail: () -> Unit) {}

    fun signOut(){}
}