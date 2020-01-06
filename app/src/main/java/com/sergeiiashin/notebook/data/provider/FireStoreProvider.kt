package com.sergeiiashin.notebook.data.provider

import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.entity.User
import com.sergeiiashin.notebook.data.exceptions.NoAuthException

class FireStoreProvider(private val fbAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = fbAuth.currentUser

    override fun getCurrentUser() = MutableLiveData<User?>().apply{
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener {snapshot, error ->
                error?.let { value = NoteResult.Error(error) }
                    ?: let {
                        snapshot?.let {
                            val notes = mutableListOf<Note>()
                            for (doc: QueryDocumentSnapshot in snapshot) {
                                notes.add(doc.toObject(Note::class.java))
                            }
                            value = NoteResult.Success(notes)
                        }
                    }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String)= MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get().addOnSuccessListener { documentSnapshot ->
                value = NoteResult.Success(documentSnapshot.toObject(Note::class.java))
            }.addOnFailureListener { error ->
                value = NoteResult.Error(error)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note)= MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id).set(note).addOnSuccessListener {
                Timber.d { "Note $note is saved" }
                value = NoteResult.Success(note)
            }.addOnFailureListener { error ->
                Timber.d { "Saving note error, message: ${error.message}" }
                value = NoteResult.Error(error)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }
}