> [!NOTE]  
> 🌐 This README is also available in [English](README.en.md).

# Kotlin Multiplatform - Windows 95

Este proyecto es una implementación con Kotlin Multiplatform (KMP) del icónico Windows 95. Combina prácticas modernas de desarrollo con una interfaz nostálgica.

----------

## 🛠 Instalación

### Requisitos

- **Kotlin 2.0.21+**
- **Compose Multiplatform 1.7.0+**
- **IDE compatible con KMP**: Android Studio o IntelliJ IDEA.
- Conocimientos básicos de Kotlin y KMP.

### Configuración

1. Clona este repositorio:

    ```bash
    git clone https://github.com/cristianmfz/Windows95.git
    ```

2. Abre el proyecto en Android Studio o IntelliJ IDEA.
3. Sincroniza el proyecto Gradle para descargar las dependencias.

----------

## ✏️ Estructura del proyecto

### Components

Puedes acceder a `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/components` para acceder a todos los componentes que se han ido creando durante el curso. Se han programado lo más genéricos posibles para poder personalizarlos y reutilizarlos en cualquier lugar. Por ejemplo **WindowsButton**

```kotlin
WindowsButton() {
    Text("Ejemplo básico")
}

WindowsButton(Modifier.height(60.dp), onClick = { print("Example") }, dotPadding = 6.dp) {
    // Cualquier vista
}
```

### Helpers

Desde `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/helpers` dispones del `SoundManager`, el encargado de reproducir la música del splash.

### Extensions

En `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/extensions` tendrás todas las funciones de extensión generadas para el proyecto.

```kotlin
Modifier.onRightClick {}

Modifier.clickableWithoutRipple {}

Modifier.rotateVertically()

// Entre otras
```

### Model

Todos los modelos de datos se encuentran en `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/model`.

### Splash

Primera vista del proyecto que se lanzará siempre que se ejecute la app. `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/screen/splash`.

### Windows95

Vista principal del proyecto `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/screen/windows95`.

----------

## 🌍 Plataformas Soportadas

- **Windows**
- **MacOS**
- **Linux**

----------

## 🤝 Contribuir

Si quieres apoyar mi trabajo puedes hacerlo a través de los siguientes medios:

- Dale una estrella al repositorio ⭐
- Sígueme en mis [redes sociales](https://cristianmfz.github.io)

¡Toda ayuda es bienvenida y me permite seguir creando proyectos!

----------

## 👨‍💻 Autor

Desarrollado por **cristianmfz**.

- [LinkedIn](https://www.linkedin.com/in/cristianmfz)
- [YouTube](https://www.youtube.com/@cristianmfz)

----------

Este es un proyecto Kotlin Multiplataform dirigido a Desktop

* `/composeApp` es para el código que se compartirá entre tus aplicaciones Compose Multiplataform.
  Contiene varias subcarpetas:
   - `commonMain` es para el código común a todos los destinos.
   - Otras carpetas son para el código Kotlin que se compilará solo para la plataforma indicada en el nombre de la carpeta.
     Por ejemplo, si quieres usar CoreCrypto de Apple para la parte iOS de tu aplicación Kotlin,
     `iosMain` sería la carpeta adecuada para dichas llamadas.


Más información sobre [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
