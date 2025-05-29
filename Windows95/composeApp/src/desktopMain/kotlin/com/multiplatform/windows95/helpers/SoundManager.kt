package com.multiplatform.windows95.helpers

import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent

class SoundManager {
    fun playSplashSound(onSoundFinished: () -> Unit) {
        val classLoader = this::class.java.classLoader
        val resource: URL? = classLoader.getResource("splashsound.wav")

        if (resource != null) {
            val audioInputStream = AudioSystem.getAudioInputStream(resource)
            val clip: Clip = AudioSystem.getClip()

            clip.addLineListener { event ->
                if (event.type == LineEvent.Type.STOP) {
                    clip.stop()
                    onSoundFinished()
                }
            }

            clip.open(audioInputStream)
            clip.start()
        } else {
            onSoundFinished()
        }
    }
}