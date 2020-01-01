package com.iashinsergei.notebook.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.iashinsergei.notebook.R
import com.iashinsergei.notebook.data.entity.Note
import com.iashinsergei.notebook.ui.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "DD.MM.YY HH:MM"
        fun start(context: Context, note: Note? = null){
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        initToolbar()

        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        initViews()
    }

    private fun initViews() {
        et_header.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let {
            et_header.setText(it.title)
            et_body.setText(it.text)
        } ?:
        et_header.setText("")
        et_body.setText("")

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
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.isTitleEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.app_name)
        }
    }
}