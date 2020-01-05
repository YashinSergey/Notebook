package com.sergeiiashin.notebook.ui.activities

import androidx.lifecycle.ViewModelProviders
import com.sergeiiashin.notebook.ui.viewmodels.SplashViewModel
import com.sergeiiashin.notebook.ui.viewstates.SplashViewState

class SplashActivity: BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel by lazy { ViewModelProviders.of(this).get(SplashViewModel::class.java) }
    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let { MainActivity.start(this) }
    }
}