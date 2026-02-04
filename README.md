[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.x-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.x-blue.svg?logo=jetpack-compose)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](license.md)
[![Build Status](https://github.com/your-username/android-compose-template/actions/workflows/android.yml/badge.svg)](https://github.com/your-username/android-compose-template/actions)

# Android Compose Template

An opinionated, production-ready template for modern Android applications.

This project serves as a comprehensive blueprint for building scalable and maintainable Android apps using 100% Kotlin, Jetpack Compose, and a cutting-edge tech stack. It's designed to help developers kickstart new projects with a solid architectural foundation.


## Features

- **100% Kotlin**: Written entirely in Kotlin, embracing modern language features.
- **Modern UI Toolkit**: Built with Jetpack Compose for a declarative and reactive UI.
- **Solid Architecture**: Implements MVVM with Clean Architecture principles.
- **Dependency Injection**: Pre-configured with Hilt for robust dependency management.
- **Asynchronous Programming**: Utilizes Kotlin Coroutines and Flow for efficient background tasks.
- **Networking**: Integrated with Retrofit for type-safe REST API communication.
- **Data Persistence**: Features Room for local database caching and DataStore for user preferences.
- **Pagination**: Includes Paging 3 for efficiently loading and displaying large datasets.
- **Navigation**: Uses Navigation-Compose for a streamlined, single-activity navigation flow.
- **Image Loading**: Leverages Coil for optimized image loading in Compose.

## Architecture

This template follows the principles of **Clean Architecture** with an **MVVM (Model-View-ViewModel)** presentation layer. This creates a clear separation of concerns that is scalable and testable.

```
+----------------+   +----------------+   +----------------+
|      UI        |   |      Domain    |   |      Data      |
| (Jetpack Compose)|   |    (Use Cases) |   | (Repositories) |
+----------------+   +----------------+   +----------------+
        |                   |                   | 
    ViewModel         Kotlin/Java Lib        Retrofit, Room
```

- **UI Layer**: Built with Jetpack Compose. `ViewModel`s provide state to the UI and handle user events.
- **Domain Layer**: Contains the core business logic of the application, encapsulated in `UseCase`s. This layer is a pure Kotlin module and is independent of the Android framework.
- **Data Layer**: Manages data from various sources (network, local database). `Repository` classes abstract the data sources from the rest of the app.

## Tech Stack

This project uses a wide range of modern libraries and tools to provide a robust starting point:

- ### Core
  - [Kotlin](https://kotlinlang.org/): Official language for Android development.
  - [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html): For asynchronous programming.
  - [Flow](https://kotlinlang.org/docs/flow.html): A reactive stream library for Kotlin.
  - [Hilt](https://dagger.dev/hilt/): For dependency injection.

- ### UI
  - [Jetpack Compose](https://developer.android.com/jetpack/compose): Modern toolkit for building native Android UI.
  - [Navigation-Compose](https://developer.android.com/jetpack/compose/navigation): For navigating between composables.
  - [Coil](https://coil-kt.github.io/coil/): Image loading library for Compose.

- ### Data
  - [Retrofit](https://square.github.io/retrofit/): Type-safe HTTP client for Android.
  - [Room](https://developer.android.com/training/data-storage/room): Local database for offline caching.
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): For loading large datasets.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore): For storing key-value pairs or typed objects.

## Getting Started

To use this template for your own project, follow these steps:

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/android-compose-template.git YourProjectName
    cd YourProjectName
    ```

2.  **Change the package name**:
    - In Android Studio, right-click on the root package `com.composetemplate`.
    - Select **Refactor -> Rename**.
    - In the dialog, choose **Rename package** and enter your desired package name.

3.  **Update `build.gradle.kts`**:
    - Open `app/build.gradle.kts`.
    - Change the `applicationId` to your new package name.

4.  **Sync Gradle**:
    - Click **Sync Now** in the toolbar to apply your changes.

5.  **Start Building**:
    - You are now ready to build your app on top of this template. Begin by adding your own features and modifying the existing modules.

**Note on Badges**: Remember to update the `Build Status` badge with your own GitHub username and repository name after setting up a CI workflow.

## License

This project is licensed under the MIT License - see the [license.md](license.md) file for details.

