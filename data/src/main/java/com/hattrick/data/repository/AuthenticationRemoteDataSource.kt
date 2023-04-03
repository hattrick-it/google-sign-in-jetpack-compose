package com.hattrick.data.repository

interface AuthenticationRemoteDataSource {
    suspend fun authenticateWithBackend(googleToken: String): String
}