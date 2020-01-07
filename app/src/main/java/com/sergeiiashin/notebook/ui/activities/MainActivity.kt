package com.sergeiiashin.notebook.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.auth.AuthUI
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.adapters.RvAdapter
import com.sergeiiashin.notebook.ui.viewmodels.MainViewModel
import com.sergeiiashin.notebook.ui.viewstates.MainViewState
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).run {
            context.startActivity(this)
        }
    }

    lateinit var adapter: RvAdapter
    override val model: MainViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initRecyclerView()

        fab.setOnClickListener(fubClickListener())
    }

    override fun onCreateOptionsMenu(menu: Menu) = MenuInflater(this)
        .inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

    private fun showLogoutDialog() {
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_positiv) {onLogout()}
            negativeButton(R.string.logout_dialog_negative) {dialog -> dialog.dismiss() }
        }.show()
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

    private fun onLogout() {
       AuthUI.getInstance().signOut(this).addOnCompleteListener {
           startActivity(Intent(this, SplashActivity::class.java))
           finish()
       }
    }

}
