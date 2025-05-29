package com.backend.di

import com.backend.repository.HeroRepository
import com.backend.repository.HeroRepositoryAlternative
import com.backend.repository.HeroRepositoryImpl
import com.backend.repository.HeroRepositoryImplAlternative
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
    single<HeroRepositoryAlternative> {
        HeroRepositoryImplAlternative()
    }
}