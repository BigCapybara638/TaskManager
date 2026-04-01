package com.example.testkmp.di

import com.example.testkmp.data.network.ApiService
import com.example.testkmp.data.network.createHttpClientEngine
import com.example.testkmp.data.repositories.ApiRepositoryImpl
import com.example.testkmp.data.repositories.FakeRepositoryImpl
import com.example.testkmp.data.repositories.SupabaseAuthRepositoryImpl
import com.example.testkmp.domain.repositories.ApiRepository
import com.example.testkmp.domain.repositories.AuthRepository
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.usecases.add.AddCategoryUseCase
import com.example.testkmp.domain.usecases.GetAllCategoriesUseCase
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import com.example.testkmp.domain.usecases.GetMessageFromGigachatUseCase
import com.example.testkmp.domain.usecases.GetTasksInCategoryUseCase
import com.example.testkmp.domain.usecases.UpdateCompletedStateUseCase
import com.example.testkmp.domain.usecases.add.AddTaskUseCase
import com.example.testkmp.domain.usecases.auth.CheckAuthorizationStateUseCase
import com.example.testkmp.domain.usecases.auth.SignInUseCase
import com.example.testkmp.domain.usecases.auth.SignOutUseCase
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import com.example.testkmp.domain.usecases.delete.DeleteCategoryUseCase
import com.example.testkmp.domain.usecases.delete.DeleteTaskUseCase
import com.example.testkmp.presentation.AuthViewModel
import com.example.testkmp.presentation.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/** Общий код для всех платформ */
val sharedModule = module {

    // UseCase`s
    factory { GetAllTasksUseCase(get()) }

    factory { GetAllCategoriesUseCase(get()) }

    factory { GetTasksInCategoryUseCase(get()) }

    factory { AddCategoryUseCase(get()) }

    factory { AddTaskUseCase(get()) }

    factory { CheckAuthorizationStateUseCase(get()) }

    factory { SignUpUseCase(get()) }

    factory { SignInUseCase(get()) }

    factory { SignOutUseCase(get()) }

    factory { UpdateCompletedStateUseCase(get()) }

    factory { GetMessageFromGigachatUseCase(get()) }

    factory { DeleteTaskUseCase(get()) }

    factory { DeleteCategoryUseCase(get()) }

    // Repositories

    single<DatabaseRepository> { FakeRepositoryImpl() }

    single<AuthRepository> { SupabaseAuthRepositoryImpl() }

    single<ApiRepository> { ApiRepositoryImpl(get()) }

    // Services
    single { ApiService(get()) }

    single<HttpClientEngine> { createHttpClientEngine() }

    single {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

}

val viewModelModule = module {

    viewModelOf(::HomeViewModel)

    viewModelOf(::AuthViewModel)

}

val appModule = module {
    includes(sharedModule, viewModelModule)
}