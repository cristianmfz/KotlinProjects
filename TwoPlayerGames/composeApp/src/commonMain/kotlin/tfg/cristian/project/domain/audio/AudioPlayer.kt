package tfg.cristian.project.domain.audio

expect class AudioPlayer {
    fun playSound(index: Int)
}

val soundResList = listOf("files/pop.mp3")