package com.sergeiiashin.notebook.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.common.format
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.viewmodels.NoteViewModel
import com.sergeiiashin.notebook.ui.viewstates.NoteViewState
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "DD.MM.YY HH:MM"
        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    override val viewModel: NoteViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java)}
    override val layoutRes: Int = R.layout.activity_note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let { viewModel.loadNote(it) }
            ?: let { supportActionBar?.title = getString(R.string.app_name)}

        initViews()
    }

    private fun initViews() {
        et_header.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let {
            et_header.setText(it.title)
            et_body.setText(it.text)
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

        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_note)
        collapsingToolbarLayout_note.isTitleEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.lastChanged?.format(DATE_TIME_FORMAT) ?: getString(R.string.app_name)
        initViews()
    }
}