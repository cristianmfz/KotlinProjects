package com.multiplatform.todoappkmp.di

import com.multiplatform.todoappkmp.data.MongoDB
import com.multiplatform.todoappkmp.domain.MongoRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.multiplatform.todoappkmp.ui.screen.home.HomeViewModel
import com.multiplatform.todoappkmp.ui.screen.task.TaskViewModel

val mongoModule = module {
    single<MongoRepository> { MongoDB() }
    factory { HomeViewModel(mongoDB = get()) }
    factory { TaskViewModel(mongoDB = get()) }
}

fun initializeKoin() {
    startKoin {
        modules(mongoModule)
    }
}