package com.hattrick.domain.repository

import com.hattrick.domain.resource.DataResult

interface AuthenticationRepository {
    suspend fun authenticateWithBackend(googleToken: String): DataResult<String>
}