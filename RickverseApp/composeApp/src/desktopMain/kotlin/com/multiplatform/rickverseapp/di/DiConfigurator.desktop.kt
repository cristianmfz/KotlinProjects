package com.multiplatform.rickverseapp.di

import com.multiplatform.rickverseapp.data.database.getDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single { getDatabase() }
    }
}