package tfg.cristian.project.di

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import tfg.cristian.project.domain.audio.AudioPlayer

@OptIn(ExperimentalResourceApi::class)
actual val targetModule = module {
    single<AudioPlayer> { AudioPlayer(context = androidContext()) }
}