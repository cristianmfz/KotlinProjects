package tfg.cristian.project

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "!Hola, ${platform.name}!"
    }

    fun farewell(): String {
        return "!Hasta pronto, ${platform.name}!"
    }
}