package com.sergeiiashin.notebook.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.provider.FireStoreProvider
import com.sergeiiashin.notebook.data.provider.RemoteDataProvider
import com.sergeiiashin.notebook.ui.viewmodels.MainViewModel
import com.sergeiiashin.notebook.ui.viewmodels.NoteViewModel
import com.sergeiiashin.notebook.ui.viewmodels.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FireStoreProvider(get(), get()) }
    single { NotesRepository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}