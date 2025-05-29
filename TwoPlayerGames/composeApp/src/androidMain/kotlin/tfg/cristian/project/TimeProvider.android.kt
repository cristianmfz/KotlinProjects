package tfg.cristian.project

actual class TimeProvider {
    actual fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}