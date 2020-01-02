package com.iashinsergei.notebook.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.iashinsergei.notebook.data.NoteResult
import com.iashinsergei.notebook.data.entity.Note

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val fireStore = FirebaseFirestore.getInstance()
    private val notesReference = fireStore.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result =  MutableLiveData<NoteResult>()
        notesReference.addSnapshotListener {snapshot, error ->
            error?.let { result.value = NoteResult.Error(error) }
                ?: let {
                    snapshot?.let {
                        val notes = mutableListOf<Note>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        result.value = NoteResult.Success(notes)
                    }
                }
        }

        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result =  MutableLiveData<NoteResult>()
        notesReference.document(id).get().addOnSuccessListener { documentSnapshot ->
            result.value = NoteResult.Success(documentSnapshot.toObject(Note::class.java))
        }.addOnFailureListener { error ->
            result.value = NoteResult.Error(error)
        }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result =  MutableLiveData<NoteResult>()
        notesReference.document(note.id).set(note).addOnSuccessListener { documentSnapshot ->
            Timber.d { "Note $note is saved" }
            result.value = NoteResult.Success(note)
        }.addOnFailureListener { error ->
            Timber.d { "Saving note error, message: ${error.message}" }
            result.value = NoteResult.Error(error)
        }
        return result
    }
}