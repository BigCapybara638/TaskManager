package com.example.testkmp.di

import com.example.testkmp.data.FakeRepositoryImpl
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.usecases.GetAllCategoriesUseCase
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import com.example.testkmp.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/** Общий код для всех платформ */
val sharedModule = module {

    factory { GetAllTasksUseCase(get()) }

    factory { GetAllCategoriesUseCase(get()) }

    single<DatabaseRepository> { FakeRepositoryImpl() }
}

val viewModelModule = module {

    viewModelOf(::HomeViewModel)

}

val appModule = module {
    includes(sharedModule, viewModelModule)
}