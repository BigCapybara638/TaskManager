package com.example.testkmp.data.network

import com.russhwolf.settings.BuildConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.okhttp.OkHttp
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.java

actual fun createHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}