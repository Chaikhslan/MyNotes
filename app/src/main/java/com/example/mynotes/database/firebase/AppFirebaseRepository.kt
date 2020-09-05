package com.example.mynotes.database.firebase

import androidx.lifecycle.LiveData
import com.example.mynotes.database.DatabaseRepository
import com.example.mynotes.models.AppNote
import com.example.mynotes.utilits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppFirebaseRepository :DatabaseRepository {

    init {
        AUTH = FirebaseAuth.getInstance()
    }

    override val allNotes: LiveData<List<AppNote>> = AllNotesLiveData()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        val idNote = REF_DATABASE.push().key.toString()
        val mapNote = hashMapOf<String,Any>()
        mapNote[ID_FIREBASE] = idNote
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text

        REF_DATABASE.child(idNote)
            .updateChildren(mapNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        REF_DATABASE.child(note.idFirebase).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: () -> Unit) {

        if(AppPreference.getInitUser()){
            initRefs()
            onSuccess()
        } else {
            AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnSuccessListener {
                    initRefs()
                    onSuccess() }
                .addOnFailureListener {
                    AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnSuccessListener {
                            initRefs()
                            onSuccess() }
                        .addOnFailureListener { onFail() }
                }
        }

    }

    private fun initRefs() {
        CURRENT_ID = AUTH.currentUser?.uid.toString()
        REF_DATABASE = FirebaseDatabase.getInstance().reference
            .child(CURRENT_ID)
    }

    override fun signOut() {
        AUTH.signOut()
    }
}