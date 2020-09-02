package com.example.mynotes.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mynotes.database.room.AppRoomDatabase
import com.example.mynotes.database.room.AppRoomRepository
import com.example.mynotes.utilits.REPOSITORY
import com.example.mynotes.utilits.TYPE_ROOM

class StartFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application

    fun InitDatabase(type: String, onSuccess: () -> Unit) {
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
            }
        }
    }
}