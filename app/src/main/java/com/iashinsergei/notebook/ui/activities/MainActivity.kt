package com.iashinsergei.notebook.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.iashinsergei.notebook.R
import com.iashinsergei.notebook.ui.adapters.RvAdapter
import com.iashinsergei.notebook.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RvAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        initRecyclerView()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.viewState().observe(this, Observer {
            it.let { adapter.notes = it.notes}
        })

        fab.setOnClickListener(fubClickListener())
    }

    private fun fubClickListener() = View.OnClickListener {
        NoteActivity.start(this)
    }

    private fun initRecyclerView() {
        rec_view_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = RvAdapter{
            NoteActivity.start(this, it)
        }
        rec_view_notes.adapter = adapter
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.isTitleEnabled = false
        supportActionBar?.title = getString(R.string.app_name)
    }

}
