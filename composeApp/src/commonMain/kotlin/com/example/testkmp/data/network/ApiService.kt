package com.example.testkmp.data.network

import com.example.testkmp.data.models.GigaChatMessage
import com.example.testkmp.data.models.GigachatApiRequest
import com.example.testkmp.data.models.GigachatApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class ApiService(
    private val client: HttpClient
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    suspend fun sendMessage(userTasks: String? = null): Result<GigachatApiResponse> {
        return try {
            val messages = mutableListOf(
                GigaChatMessage(
                    role = "system",
                    content = "Тебе прийдет список дел. Ты должен в двух предложениях не списком а цельным текстом ответить, что нужно будет сделать в течении дня, не от своего имени"
                )
            )

            val tasksContent = if (!userTasks.isNullOrBlank()) {
                "Список дел: $userTasks"
            } else {
                "Список дел: помыть посуду 14:00, забрать ребенка из садика - 12:00, поприседать 21:00, отжимания - 20:50, поучить английский - в течении всего дня"
            }

            messages.add(
                GigaChatMessage(
                    role = "user",
                    content = tasksContent
                )
            )

            val request = GigachatApiRequest(
                model = "GigaChat-Pro",
                messages = messages
            )

            val url = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions"

            val response = client.post(url) {
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/json")
                header("Authorization", "Bearer $TOKEN")
                setBody(request)
            }

            val body = response.bodyAsText()

            if (response.status.isSuccess()) {
                val result = json.decodeFromString<GigachatApiResponse>(response.bodyAsText())
                Result.success(result)
            } else {
                Result.failure(Exception("HTTP ${response.status.value}: $body"))
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    companion object {
        private const val TOKEN = "eyJjdHkiOiJqd3QiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.yX-mCCdfWOeAIzramLHhAvqlcmg7arZ7FMhRpRCm9Bed7JFDwUTN_BxEPnxcmTuCnNqDFpgoEpgQCHstKBsCWtAshKQnjsBvt-HllheU6ZBX1rsQMvK7CJ327QujgNNv_WL5q7V2iNJOJyKAW4Q7_CPzOSxEo1paNErTmtk9Xa6ZMTjH7zJ44Jrzp2URQLAskZyB5eEEysQD1sOU5P2fa92swyFunJBJuF3LBe7E6abBM1CmuVv_8BrFOdRrvJm29d0KVfVwxAfnYzHe3q7okd1bIXlJk8UTfHiNFrHubx-EJGZH0bs0Fg-re7UlymCw3iz01iQqhd5GSfbZ9l_Urg.IBzRMpAQqKS28bKRHuX4Fw.tzFmONFPmNsShVP-e-q_JPwVRSS9UI3J9pIOsAZY1e3kCGqzAyUnO8zhmTrtJyZgfurKguAhstsq8NTgzRyduedzsU3Fr3q6E9UMKxNoPNHT_dh1AroD6iSMWpyord4Ce9gT5yW0FSk_dIsfrT2HoTjQLu9sLdZManzffmi6-zA-e9qksllhTeIvongtE1TbM4mf5uxFIh9u1fGVu8RstO6GVYgFQVFsiktzNhVnfRy6aeHyFQWuY3Q3N92eY-2KHPKX_mPcI-ZFwIKz3oEfj3ykOdjU0FZnwhrVf0ZE9wb6L71l0WgZSXFQlKUBCN7RwswMWc9NA6FGfLQQwK_E0CjF7K6t0FYcbmINlp5TG-g3HONfeFfyM-bKX_0QEQ_t83xeQQzmquO-I0CiCC6ZIRw1mDbe9vDNbcEd-voK-FGeaQHcENogogowFoHvDnLJNc24zpyR6EHyci0GMt0s2M8Yy-trUSWM988k5PvtJprJll76nOziJ35EGg8wkGEDhJZ8RBr8ZyA2Z81hW7w7S1qu3W_zRXZcYRZG_l6VbtYK4lCepnOy0JcM995vzwSKzyjuBYJ5CaWA0hgf806dj4Hk19TQRhLAwQvdcXLU-w2LeAJcC0I_t797Vc7FCBC1HzbjQGVFj4aEJoLtwt3SzHdtaXfBL6GvUq1w8niZOvONs_Mfrl3JaS47u8fjn4Xzp-Syd6w8XxgJLekKL5pjB3zEoAuvQhXuAE07jw82xxg.Y0V4jkpwR8EG2NnIHw6i5qfXwcvNBKG7IcyK9XxvWsU"
    }
}