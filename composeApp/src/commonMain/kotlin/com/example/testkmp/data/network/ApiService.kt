package com.example.testkmp.data.network

import com.example.testkmp.data.models.GigaChatMessage
import com.example.testkmp.data.models.GigachatApiRequest
import com.example.testkmp.data.models.GigachatApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.github.jan.supabase.logging.LogLevel
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
@Serializable
data class S(
    val `object`: String,
    val data: List<Bdaaaa>
)

@Serializable
data class Bdaaaa(
    val id: String,
    val `object`: String,
    val owned_by: String,
    val type: String
)
class ApiService(
    private val client: HttpClient
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    suspend fun sendMessage(userTasks: String? = null): Result<S> {
        return try {
            // Формируем сообщения
            val messages = mutableListOf(
                GigaChatMessage(
                    role = "system",
                    content = "Тебе прийдет список дел. Ты должен в двух предложениях не списком а цельным текстом ответить, что нужно будет сделать в течении дня, не от своего имени"
                )
            )

            // Если есть задачи, добавляем их
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

            val request = GigachatApiRequest(model = "-Pro", messages = messages)

            // URL точно как в Postman
            //val url = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions"

            val url = "https://gigachat.devices.sberbank.ru/api/v1/models"

            println("=== SENDING REQUEST ===")
            println("URL: $url")
            println("Headers:")
            println("  Content-Type: application/json")
            println("  Accept: application/json")
            println("  Authorization: Bearer ${TOKEN.take(50)}...")
            println("Body: ${json.encodeToString(request)}")
            println("========================")

            val response = client.get(url) {
                header("Accept", "application/json")
                //header("Content-Type", "application/json")
                header("Authorization", "Bearer $TOKEN")
                //setBody(request)
            }

            println()
            println("QQQQQQQQQQQQ $response")

            val body = response.bodyAsText()
            println("=== RESPONSE ===")
            println("Status: ${response.status.value}")
            println("Body: $body")
            println("================")

            if (response.status.isSuccess()) {
                val result = json.decodeFromString<S>(response.bodyAsText())
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
        // Используйте ТОТ ЖЕ токен, что работает в Postman
        private const val TOKEN = "eyJjdHkiOiJqd3QiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.FFfh1e4GHUnpUfCh4WZYcd7OJRvFp1kY-wEn2ZrQfmjBqpkJ1W2Tm6yToEgdYkOYeiZmVcAicmzI9i5C9YsFPis0Zm5APJjBlsAsbHyjs9YataSJMmm5QVd-mkh7DoWEfN28n9CMYm2VfdioROqbW5pljEHXbvTGahwhfanL20uyC-MxpfLopze_4_o-SUjnpHUPXSgRKuNS8rnMTqmqW_aon5y0HZsoLQkPULH0MWqBbW2Sg0xiu3EXV2HgNkwDBHzmvG89cC_0gKgkiS5sQcXhiBvubJ3LNmt74siJV5sMPzCvM9LIuzLDOny25tsdygnM30C-4IC_VxMG4WqPfQ.VY0gCOfzyu7sV3JCpyVaQQ.YbFhuD6ylpXsncluPtK6ze_ZdzNSLedDGsd_hFwCtVfWEmN2SgMjOR9fAWRD6Vlv8mDqUyb6WYV6cV6k0FF5ljh9cOKlXCcDWk4q-q2chBFkksfuwlyHY-A72cwp4abUpbYdgp3tgyG7jt2tEj-4nHW1M0cs3zKvQWtEY8LPAkVyj08yUWcXwyExs6o7jmomv2wQsoTx9_wAoyWXnkFPpxMnRXBmhMa3vWn4HAseHuY2_6RYISjXNI5f2hAzDWxS3ufVH1HKQhZHMkpi8jOM8htYxSq5q_O1DzWBHPWdLgWrveLhY92JqQSqUTY27hMS-w7yRCXYz8uM-WHbMTqwWoNzqp6op1D39gndT8o2hFejqnxSy2d_mjk27AALNuY4oIda89bHW8r4N2R72Ykaz1l4JaoAyThOF2H6LvSb2xZR1j6hdgymIWfLxiAPCR-Kv7AGrAd0wyOUTMxoNfe7Cpopx4qBLTWLfhmCYLvWt3ymu1PhnRgkQZZ5V2uOjg4GSt-wmLG6wjOqyhJWtIHvDGw4A3wSXtWbezneaWk1hWRUukSMv2LQrRE-Dbb6rLXn8xkB71k9mh7pP91PoMLFwxrA_oNh21Y7GyP9rvrhPfRGVS0ywAAOPs6f49RvzE2YGyVnLX9_D-n7ubVqoXVWq1RzhoACrH3yRYdJ3dwky7579JqTe7_dhNhjPZBrP9tkGJh6JdE3jKUOMaDaTqhgEeA9GODtDHDD--eN2jF7Yp4.WLKzH7xe0YafxSgAlds-6BMQ1mebc0320dxYbM0En5s"
    }
}