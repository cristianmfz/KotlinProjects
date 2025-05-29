package tfg.cristian.project

expect class TimeProvider() {
    fun getCurrentTimeMillis(): Long
}