package com.sergeiiashin.notebook.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sergeiiashin.notebook.R

class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "_TAG"
        fun createInstance() = LogoutDialog()
    }

    interface LogoutListener {
        fun onLogout()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog = AlertDialog.Builder(context)
        .setTitle(getString(R.string.logout_dialog_title))
        .setMessage(getString(R.string.logout_dialog_message))
        .setPositiveButton(getString(R.string.logout_dialog_positiv)) { _, _ -> (activity as LogoutListener).onLogout()}
        .setNegativeButton(getString(R.string.logout_dialog_negative)) { _, _ -> dismiss()}
        .create()
}