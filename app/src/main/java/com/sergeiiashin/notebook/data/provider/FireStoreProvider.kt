package com.sergeiiashin.notebook.data.provider

import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.entity.Note

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val fireStore by lazy { FirebaseFirestore.getInstance() }
    private val notesReference by lazy {fireStore.collection(NOTES_COLLECTION)}

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        notesReference.addSnapshotListener {snapshot, error ->
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
    }

    override fun getNoteById(id: String)= MutableLiveData<NoteResult>().apply {
        notesReference.document(id).get().addOnSuccessListener { documentSnapshot ->
            value = NoteResult.Success(documentSnapshot.toObject(Note::class.java))
        }.addOnFailureListener { error ->
            value = NoteResult.Error(error)
        }
    }

    override fun saveNote(note: Note)= MutableLiveData<NoteResult>().apply {
        notesReference.document(note.id).set(note).addOnSuccessListener {
            Timber.d { "Note $note is saved" }
            value = NoteResult.Success(note)
        }.addOnFailureListener { error ->
            Timber.d { "Saving note error, message: ${error.message}" }
            value = NoteResult.Error(error)
        }
    }
}