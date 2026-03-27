package com.example.testkmp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient(private val engine: HttpClientEngine = createHttpClientEngine()) {

    /** Фабричный метод для создания в платформозависимом коде.
     * Здесь он избыточен, но я решил его оставить потому что хотел потренироваться
     * делать фабрики объектов*/
//    companion object {
//        fun create(engine: HttpClientEngine) : ApiClient = ApiClient(engine)
//    }

    val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("https://gigachat.devices.sberbank.ru/api/v1")
            header("Content-Type", "application/json")
        }
    }


}