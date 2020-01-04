package com.sergeiiashin.notebook.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.adapters.RvAdapter
import com.sergeiiashin.notebook.ui.viewmodels.MainViewModel
import com.sergeiiashin.notebook.ui.viewstates.MainViewState
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    lateinit var adapter: RvAdapter
    override val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java)}
    override val layoutRes: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initRecyclerView()

        fab.setOnClickListener(fubClickListener())
    }

    private fun fubClickListener() = View.OnClickListener {
        NoteActivity.start(this)
    }

    private fun initRecyclerView() {
        rec_view_notes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = RvAdapter{
            NoteActivity.start(this, it.id)
        }
        rec_view_notes.adapter = adapter
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.isTitleEnabled = false
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

}
