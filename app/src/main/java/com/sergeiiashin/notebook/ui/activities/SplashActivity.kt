package com.sergeiiashin.notebook.ui.activities

import com.sergeiiashin.notebook.ui.viewmodels.SplashViewModel
import com.sergeiiashin.notebook.ui.viewstates.SplashViewState
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<Boolean?, SplashViewState>() {

    override val model: SplashViewModel by viewModel()
    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let { MainActivity.start(this) }
    }
}