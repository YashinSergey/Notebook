package com.iashinsergei.notebook.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iashinsergei.notebook.R
import com.iashinsergei.notebook.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.viewState().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
