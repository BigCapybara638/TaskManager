package com.example.testkmp.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import java.io.File
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.TrustManagerFactory

actual fun createHttpClientEngine(): HttpClientEngine {
    return CIO.create {
        https {
            // Для CIO можно использовать системные сертификаты
            // или добавить свои через Java KeyStore
            try {
                val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                    load(null, null)
                }

                val certificates = listOf(
                    "russian_trusted_root_ca.crt",
                    "russian_trusted_sub_ca.crt"
                )

                certificates.forEach { certName ->
                    val certStream = File("composeResources/files/$certName").inputStream()
                    val cert = CertificateFactory.getInstance("X.509").generateCertificate(certStream)
                    keyStore.setCertificateEntry(certName, cert)
                    certStream.close()
                }

                val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                tmf.init(keyStore)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}