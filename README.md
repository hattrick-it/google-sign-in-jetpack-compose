## Google Sign-in with Jetpack Compose MVVM clean architecture

* An effortless implementation of Google Sign-in with Jetpack Compose MVVM architecture

## Content

### Architecture
The application is divided in three layers, each one in a different module:

* Data
    * Repository implementation

* Domain
    * Repository interface
    * Custom error
    * DataResult success/failure wrapper
    * Use case

* App
    * Jetpack Compose
        * Login and Main screens using compose functions
        * State handling with ViewModel using stateflow
    * Activities
        * Login and Main Activity
        * Navigation from Login to Main

### Additional features

* Dependency injection with Koin



### Naming:

* Refactor package com.hattrick.myapplication to desired package name.
* Update app/build.gradle -> defaultConfig -> applicationId
* Update strings.xml -> "app_name"
* Update settings.gradle -> rootProject.name
