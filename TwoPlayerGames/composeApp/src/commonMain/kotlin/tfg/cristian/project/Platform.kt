package tfg.cristian.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform