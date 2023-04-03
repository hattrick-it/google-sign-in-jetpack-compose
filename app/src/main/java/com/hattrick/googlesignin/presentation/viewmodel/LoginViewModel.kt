package com.hattrick.googlesignin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hattrick.domain.usecase.AuthenticateWithBackendUseCase
import com.hattrick.googlesignin.presentation.viewmodelstate.LoginStatus
import com.hattrick.googlesignin.presentation.viewmodelstate.LoginViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticateWithBackendUseCase: AuthenticateWithBackendUseCase
) : ViewModel() {
    private val loginViewModelState = MutableStateFlow(LoginViewModelState())

    val uiState = loginViewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            loginViewModelState.value
        )

    fun authenticateWithBackend(googleToken: String) {
        loginViewModelState.update { it.copy(loginStatus = LoginStatus.Loading) }
        viewModelScope.launch {
            authenticateWithBackendUseCase(googleToken).checkResult(
                onSuccess = {loginViewModelState.update { it.copy(loginStatus = LoginStatus.Success) }},
                onError = {loginViewModelState.update { it.copy(loginStatus = LoginStatus.Failure) }}
            )
        }
    }


    fun onLoginError() {
        loginViewModelState.update { it.copy(loginStatus = LoginStatus.Failure) }
    }
}