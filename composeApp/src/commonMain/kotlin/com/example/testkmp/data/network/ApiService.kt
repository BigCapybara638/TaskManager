package com.example.testkmp.data.network

import com.example.testkmp.data.models.GigaChatMessage
import com.example.testkmp.data.models.GigachatAccessTokenResponse
import com.example.testkmp.data.models.GigachatApiRequest
import com.example.testkmp.data.models.GigachatApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
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

    suspend fun getBearerKey() : Result<GigachatAccessTokenResponse> {
        return try {

            val url = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth"

            val response: GigachatAccessTokenResponse = client.post(url) {
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                header("RqUID", "ed3e41e1-747e-44db-b597-09caa76c489e")
                header(HttpHeaders.Authorization, "Basic $TOKEN")
                setBody("scope=GIGACHAT_API_PERS")
            }.body()

            Result.success(response)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun sendMessage(userTasks: String? = null): Result<GigachatApiResponse> {
        return try {
            val url = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions"

            val bearerKey = getBearerKey().getOrNull()?.access_token ?: ""
            println("fhfh $bearerKey")

            val messages = mutableListOf(
                GigaChatMessage(
                    role = "system",
                    content = "Тебе прийдет список дел. Ты должен в двух предложениях не списком а цельным текстом ответить, что нужно будет сделать в течении дня, не от своего имени"
                )
            )

            val tasksContent = if (!userTasks.isNullOrBlank()) {
                "Список дел: $userTasks"
            } else {
                "Сегодня дел нет"
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

            val response = client.post(url) {
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/json")
                header("Authorization", "Bearer $bearerKey")
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
        private const val TOKEN = "MDE5YmY1MTQtYzMwOC03MzQzLWJiMzAtYWYyNTJlYzRiOWZhOjMyOTcxMDFjLTlhNjgtNGYyOS1iOTY3LTVmNmY2ZDljMTE3OA=="
    }
}