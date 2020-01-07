package com.sergeiiashin.notebook.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import com.sergeiiashin.notebook.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class BaseActivity<S> : AppCompatActivity(), CoroutineScope {

    companion object {

        private const val SIGN_IN = 7531
    }

    override val coroutineContext: CoroutineContext by lazy { Dispatchers.Main + Job() }

    abstract val model: BaseViewModel<S>
    abstract val layoutRes: Int?
    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()

        dataJob = launch {
            model.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            model.getErrorChannel().consumeEach { error ->
                renderError(error)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    abstract fun renderData(data: S)

    private fun showError(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun renderError(error: Throwable?) = error?.let {
        when (error) {
            is NoAuthException -> startLogin()
            else -> it.message?.let { message ->
                showError(message)
            }
        }
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()
            , SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}