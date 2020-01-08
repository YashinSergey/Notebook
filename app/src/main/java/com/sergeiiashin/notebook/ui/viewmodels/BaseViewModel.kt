package com.sergeiiashin.notebook.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
open class BaseViewModel <S> : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy { Dispatchers.Default + Job() }

    private val viewStateChannel = BroadcastChannel<S>(Channel.CONFLATED)
    private val errorChannel = Channel<Throwable>()

    open fun getViewState(): ReceiveChannel<S> = viewStateChannel.openSubscription()
    open fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel

    protected fun setData(data: S) {
        launch { viewStateChannel.send(data) }
    }

    protected fun setError(error: Throwable) {
        launch { errorChannel.send(error) }
    }

    override fun onCleared() {
        viewStateChannel.close()
        errorChannel.close()
        coroutineContext.cancel()
        super.onCleared()
    }
}