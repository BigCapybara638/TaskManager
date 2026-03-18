package com.example.testkmp.di

import com.example.testkmp.data.FakeRepositoryImpl
import com.example.testkmp.data.SupabaseAuthRepositoryImpl
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.repositories.AuthRepository
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.usecases.AddCategoryUseCase
import com.example.testkmp.domain.usecases.GetAllCategoriesUseCase
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import com.example.testkmp.domain.usecases.GetTasksInCategoryUseCase
import com.example.testkmp.domain.usecases.auth.CheckAuthorizationState
import com.example.testkmp.domain.usecases.auth.SignInUseCase
import com.example.testkmp.domain.usecases.auth.SignOutUseCase
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import com.example.testkmp.presentation.AuthViewModel
import com.example.testkmp.presentation.HomeViewModel
import io.github.jan.supabase.SupabaseClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/** Общий код для всех платформ */
val sharedModule = module {

    factory { GetAllTasksUseCase(get()) }

    factory { GetAllCategoriesUseCase(get()) }

    factory { GetTasksInCategoryUseCase(get()) }

    factory { AddCategoryUseCase(get()) }

    factory { CheckAuthorizationState(get()) }

    factory { SignUpUseCase(get()) }

    factory { SignInUseCase(get()) }

    factory { SignOutUseCase(get()) }

    single<DatabaseRepository> { FakeRepositoryImpl() }

    single<AuthRepository> { SupabaseAuthRepositoryImpl() }
}

val viewModelModule = module {

    viewModelOf(::HomeViewModel)

    viewModelOf(::AuthViewModel)

}

val appModule = module {
    includes(sharedModule, viewModelModule)
}