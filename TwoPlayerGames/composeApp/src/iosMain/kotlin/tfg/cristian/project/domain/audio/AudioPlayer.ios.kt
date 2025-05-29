package tfg.cristian.project.domain.audio

import kotlinx.cinterop.ExperimentalForeignApi
import me.sample.library.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

@OptIn(ExperimentalResourceApi::class)
actual class AudioPlayer {
    private val mediaItems = soundResList.map { path ->
        val uri = Res.getUri(path)
        NSURL.URLWithString(URLString = uri)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun playSound(index: Int) {
        val avAudioPlayer = AVAudioPlayer(mediaItems[index]!!, error = null)
        avAudioPlayer.prepareToPlay()
        avAudioPlayer.play()
    }
}