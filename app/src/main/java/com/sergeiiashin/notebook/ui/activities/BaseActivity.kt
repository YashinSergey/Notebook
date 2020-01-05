package com.sergeiiashin.notebook.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import com.sergeiiashin.notebook.ui.viewmodels.BaseViewModel
import com.sergeiiashin.notebook.ui.viewstates.BaseViewState

abstract class BaseActivity<T, S: BaseViewState<T>> : AppCompatActivity() {

    companion object {
        private const val SIGN_IN = 7531
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }

        viewModel.getViewState().observe(this, Observer<S> {
            it ?: return@Observer
            it.error?.let { error ->
                renderError(error)
                return@Observer
            }
            renderData(it.data)
        })
    }

    abstract fun renderData(data: T)

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
//        super.onActivityResult(requestCode, resultCode, data)
    }
}