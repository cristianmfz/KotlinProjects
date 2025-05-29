package tfg.cristian.project.domain.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import me.sample.library.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
actual class AudioPlayer(context: Context) {
    private val mediaPlayer = ExoPlayer.Builder(context).build()
    private val mediaItems = soundResList.map {
        MediaItem.fromUri(Res.getUri(it))
    }

    init {
        mediaPlayer.prepare()
    }

    actual fun playSound(index: Int) {
        mediaPlayer.setMediaItem(mediaItems[index])
        mediaPlayer.play()
    }
}