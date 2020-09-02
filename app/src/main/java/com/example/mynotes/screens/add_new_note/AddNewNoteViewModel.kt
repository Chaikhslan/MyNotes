package com.example.mynotes.screens.add_new_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.models.AppNote
import com.example.mynotes.utilits.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewNoteViewModel(application: Application):AndroidViewModel(application) {
    fun insert(note: AppNote, onSuccess:()->Unit) =
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.insert(note){
                onSuccess()
            }
        }
}