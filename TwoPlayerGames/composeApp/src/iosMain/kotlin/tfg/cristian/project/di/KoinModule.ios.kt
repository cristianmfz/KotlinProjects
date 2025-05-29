package tfg.cristian.project.di

import org.koin.dsl.module
import tfg.cristian.project.domain.audio.AudioPlayer

actual val targetModule = module {
    single<AudioPlayer> { AudioPlayer() }
}