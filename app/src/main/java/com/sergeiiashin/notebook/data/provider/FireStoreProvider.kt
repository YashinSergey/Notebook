package com.sergeiiashin.notebook.data.provider

import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.entity.User
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireStoreProvider(private val fbAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = fbAuth.currentUser

    override suspend fun getCurrentUser(): User? = suspendCoroutine {  continuation ->
        continuation.resume(currentUser?.let { User(it.displayName ?: "", it.email ?: "")})
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    @ExperimentalCoroutinesApi
    override fun subscribeToAllNotes(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null
        try {
            registration = getUserNotesCollection().addSnapshotListener {snapshot, error ->
                val value = error?.let { NoteResult.Error(error) }
                    ?: let {
                        snapshot?.let { it ->
                            val notes = it.documents.map { it.toObject(Note::class.java) }
                            NoteResult.Success(notes)
                        }
                    }
                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(NoteResult.Error(e))
        }
        invokeOnClose { registration?.remove() }
    }

    override suspend fun getNoteById(id: String): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(id).get().addOnSuccessListener { documentSnapshot ->
                continuation.resume(documentSnapshot.toObject(Note::class.java)!!)
            }.addOnFailureListener { error ->
                continuation.resumeWithException(error)
            }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(note.id).set(note).addOnSuccessListener {
                Timber.d { "Note $note is saved" }
                continuation.resume(note)
            }.addOnFailureListener { error ->
                Timber.d { "Saving note error, message: ${error.message}" }
                continuation.resumeWithException(error)
            }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun deleteNote(noteId: String): Unit = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(noteId).delete().addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener { error ->
                Timber.d { "Deleted note error, message: ${error.message}" }
                continuation.resumeWithException(error)
            }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }
}