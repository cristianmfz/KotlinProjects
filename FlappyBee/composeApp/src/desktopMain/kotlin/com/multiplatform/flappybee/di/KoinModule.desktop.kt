package com.multiplatform.flappybee.di

import com.multiplatform.flappybee.domain.AudioPlayer
import org.koin.dsl.module

actual val targetModule = module {
    single<AudioPlayer> { AudioPlayer() }
}