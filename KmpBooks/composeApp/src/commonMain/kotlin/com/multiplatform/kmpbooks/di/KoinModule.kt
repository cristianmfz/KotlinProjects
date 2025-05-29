package com.multiplatform.kmpbooks.di

import com.multiplatform.kmpbooks.data.getRoomDatabase
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*
import com.multiplatform.kmpbooks.ui.screen.home.HomeViewModel
import com.multiplatform.kmpbooks.ui.screen.manage.ManageViewModel
import com.multiplatform.kmpbooks.ui.screen.detail.DetailViewModel

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::ManageViewModel)
    viewModelOf(::DetailViewModel)
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}