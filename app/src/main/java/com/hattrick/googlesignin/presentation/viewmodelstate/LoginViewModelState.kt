package com.hattrick.googlesignin.presentation.viewmodelstate

data class LoginViewModelState(
    val loginStatus: LoginStatus = LoginStatus.Initial
)

enum class LoginStatus {
    Initial,
    Success,
    Loading,
    Failure
}