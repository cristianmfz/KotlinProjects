> [!NOTE]  
> 🌐 Este README también está disponible en [Español](README.md).

# Kotlin Multiplatform - Windows 95

This project is a Kotlin Multiplatform (KMP) implementation of the iconic Windows 95. It combines modern development practices with a nostalgic interface.

----------

## 🛠 Installation

### Requirements

- **Kotlin 2.0.21+**
- **Compose Multiplatform 1.7.0+**
- **KMP compatible IDE**: Android Studio or IntelliJ IDEA.
- Basic knowledge of Kotlin and KMP.

### Setup

1. Clone this repository:

    ```bash
    git clone https://github.com/cristianmfz/Windows95.git
    ```

2. Open the project in Android Studio or IntelliJ IDEA.
3. Sync the Gradle project to download dependencies.

----------

## ✏️ Project Structure

### Components

Find all components in `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/components`. The components are designed to be as generic as possible to allow customization and reusability. Example: **WindowsButton**

```kotlin
WindowsButton() {
    Text("Basic example")
}

WindowsButton(Modifier.height(60.dp), onClick = { print("Example") }, dotPadding = 6.dp) {
    // Any view
}
```

### Helpers

In `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/helpers`, you’ll find the `SoundManager`, responsible for playing the splash music.

### Extensions

All extension functions are located in `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/extensions`.

```kotlin
Modifier.onRightClick {}

Modifier.clickableWithoutRipple {}

Modifier.rotateVertically()

// And more
```

### Model

All data models are in `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/model`.

### Splash

The first screen of the project, always launched when the app starts: `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/screen/splash`.

### Windows95

The main screen of the project: `ComposeApp/src/desktopMain/kotlin/com/cristianmfz/windows95/ui/screen/windows95`.

----------

## 🌍 Supported Platforms

- **Windows**
- **MacOS**
- **Linux**

----------

## 🤝 Contributing

If you’d like to support my work, you can do so through the following means:

- Give a star to this repository ⭐
- Follow me on my [social media](https://cristianmfz.github.io)

Every contribution is welcome and helps me continue creating projects!

----------

## 👨‍💻 Author

Developed by **cristianmfz**.

- [LinkedIn](https://www.linkedin.com/in/cristianmfz)
- [YouTube](https://www.youtube.com/@cristianmfz)

----------

This is a Kotlin Multiplatform project targeting Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
   - `commonMain` is for code that’s common for all targets.
   - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
     For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
     `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
