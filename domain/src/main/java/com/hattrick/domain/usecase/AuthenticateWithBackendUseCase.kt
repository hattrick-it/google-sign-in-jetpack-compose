package com.hattrick.domain.usecase

import com.hattrick.domain.repository.AuthenticationRepository
import com.hattrick.domain.resource.CustomError
import com.hattrick.domain.resource.DataResult

class AuthenticateWithBackendUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(googleToken: String): DataResult<String> =
        authenticationRepository.authenticateWithBackend(googleToken).checkResultAndReturn(
            onSuccess = { DataResult.Success(it) },
            onError = { DataResult.Error(it) }
        )
}