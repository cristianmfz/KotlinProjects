package com.multiplatform.kmpbooks.di

import com.multiplatform.kmpbooks.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder() }
}