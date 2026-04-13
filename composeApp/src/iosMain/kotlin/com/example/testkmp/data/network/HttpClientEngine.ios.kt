package com.example.testkmp.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.ChallengeHandler
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.runBlocking
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.credentialForTrust
import platform.Foundation.serverTrust
import com.exapmle.testkmp.generated.resources.Res

actual fun createHttpClientEngine(): HttpClientEngine {
    return Darwin.create {
        configureSession {
            timeoutIntervalForRequest = 10.0
        }
    }
}