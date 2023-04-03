package com.hattrick.googlesignin.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.hattrick.data.repository.AuthenticationRemoteDataSource
import com.hattrick.data.repository.AuthenticationRemoteDataSourceImpl
import com.hattrick.data.repository.AuthenticationRepositoryImpl
import com.hattrick.domain.repository.AuthenticationRepository
import com.hattrick.domain.usecase.AuthenticateWithBackendUseCase
import com.hattrick.googlesignin.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//Complete the CLIENT_ID with your own client id
const val CLIENT_ID = ""

val appModule = module {
    viewModel { LoginViewModel(get()) }

    factory { AuthenticateWithBackendUseCase(get()) }
    factory<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
    factory<AuthenticationRemoteDataSource> { AuthenticationRemoteDataSourceImpl() }

    single { Dispatchers.IO }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
    }

    single { getGoogleSignInClient(androidContext()) }
}