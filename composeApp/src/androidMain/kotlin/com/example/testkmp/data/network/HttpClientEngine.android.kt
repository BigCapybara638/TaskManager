package com.example.testkmp.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun createHttpClientEngine(): HttpClientEngine {
    return Android.create()
}