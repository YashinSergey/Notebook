package com.sergeiiashin.notebook.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.common.format
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.viewmodels.NoteViewModel
import com.sergeiiashin.notebook.ui.viewstates.NoteData
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalCoroutinesApi
class NoteActivity : BaseActivity<NoteData>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "DD.MM.YY"
        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    override val model: NoteViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let { model.loadNote(it) }
            ?: let { supportActionBar?.title = getString(R.string.app_name)}

        initViews()
    }

    private fun initViews() {
        et_header.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let {
            if(et_header.text.toString() != it.title){
                et_header.setText(it.title)
            }
            if(et_body.text.toString() != it.text){
                et_body.setText(it.text)
            }
            supportActionBar?.title = note?.lastChanged?.format(DATE_TIME_FORMAT)
        } ?: let {
            supportActionBar?.title =  getString(R.string.app_name)
        }

        et_header.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun saveNote() {
        if (et_header.text == null || et_body.text!!.length < 3 ) return

        note = note?.copy(
            title = et_header.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_header.text.toString(), et_body.text.toString())

        note?.let { model.save(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu) = MenuInflater(this)
        .inflate(R.menu.menu_note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_note)
        collapsingToolbarLayout_note.isTitleEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun renderData(data: NoteData) {
        if (data.isDeleted) {
            finish()
            return
        }
        this.note = data.note
        initViews()
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.note_delete_message
            negativeButton(R.string.note_delete_cancel) {dialog -> dialog.dismiss() }
            positiveButton(R.string.note_delete_ok) {model.deleteNote()}
        }.show()
    }
}