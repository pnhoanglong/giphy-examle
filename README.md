# Giphy

**CircleCI build status:** [![CircleCI](https://circleci.com/gh/pnhoanglong/giphy-examle/tree/develop.svg?style=svg)](https://circleci.com/gh/pnhoanglong/giphy-examle/tree/develop)

Giphy is a demo application showing images from [GIPHY](https://giphy.com/). It contains two screens:
* Screen 1:
  * Display lasted  images from Giphy by getting data from Giphy’s Trending API.
  * The images are displayed in still format.
  * An infinite scrolling mechanism is implemented where, whenever the user scrolls  to the bottom of the list, it should be populated with more images.
* Screen 2:
  * Upon tapping on any of the images in Screen 1, this new screen should open up, which displays the animated version of this GIF.
  * This screen should also make use of Giphy’s Random API to automatically load a new random animated GIF (in the file format of your liking) every 10 seconds.
  * Upon tapping back on this view, the user should be taken back to Screen 1.


## Application Architecture
### Programing Language:
Kotlin v1.2.30
### Implementation
The architecture of this application is **Model-View-ViewModel**. This MVVM architecture is choose because:
* Separation the responsibility of the classes: The Android framework Activity and Fragment classes just show and update UI, The Model classes store data which is used to display UI (as well as processing their data); and The ViewModel classes provide the data for the view by exposing their model. This separation makes it easier to maintain and extend application.
* Testability: In MVVM architecture, ViewModel classes do not reference the View class, which make them independent from Android framework class. It helps test application easier because ViewModel classes usually handle logic.

The reactive programing is also applied in this project: The ViewModel class provide `observable` data for the View class. If data changes, the view which observe it will be notified to update UI.

The dependencies injection technique is used in application, which helps de-coupling the classes. A class does not create instances of their dependent classes, they are injected.

The [CircleCI](https://circleci.com/gh/pnhoanglong/giphy-examle) is used to apply continuous integration in this application. The curret configuration is to run `lint` and `unitTest` when there is a new commit pushed to github repository.

### Libraries:
This section will list out some main libraries application is using.

## Android Architecture Components
* Android architecture components are part of Android Jetpack. They are a collection of libraries that help you design robust, testable, and maintainable apps.
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) is used to build observable data object, it helps the View classess notified to update UI when data changes.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) is used a ViewModel base classes in MVVM architecture. It stores UI-related data that isn't destroyed on app rotations
  * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) is to automatically handle Android component (e.g.: activity, fragment) lifecycle related events. For example, it eliminate the crash because AsyncTask.onPostExecuted() is triggered when fragment is stopped.



* [Dagger](https://google.github.io/dagger/) is used for dependencies injection technique.
* [GIPHY Core SDK](https://github.com/Giphy/giphy-android-sdk-core) is a official wrapper around Giphy API.
* [Glide](https://bumptech.github.io/glide/) is a fast and efficient image loading library for Android focused on smooth scrolling. Glide supports fetching, decoding, and displaying video stills, images, and animated GIFs.
* [JUnit](https://github.com/mockito/mockito) and [Mockito](https://github.com/mockito/mockito) is used to unit testing application.

## How to build
### Requirements
* Android Studio v3.1.3
* Android SDK
### Building configurations
* Gradle v4.4
* Gradle Android Buildtool v3.1.3
* Android SDK API 27
### Setup
1. Clone this project:
```
git clone git@github.com:pnhoanglong/giphy-examle.git
```
2. Open the project in Android Studio. Verify the following are installed in `Tools > SDK Manager > System Setting > Android SDK`:
  * SDK Platform Android 8.1 (API level 27)
  * Android SDK Platform-Tools
  * Android SDK Tools
  * Google Play Service * ConstrainLayout for Android
  * Solver for ConstrainLayout
  * Android Support Repository
  * Google Repository

3. Wait for Gradle sync to finish.

4. To run the app, simply click **Run**  ![alt text](https://camo.githubusercontent.com/4b94cee2759faf24f54eedd56b9c725f6f8996f3/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f696d616765732f746f6f6c732f61732d72756e2e706e67). If you are prompted to select a device, choose one where you want the application to be installed. This will build and generate an apk file which will then be installed to your device.





## Demo

### Screenshots

![Screen 1](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/screen1.png) ![Screen 2](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/screen2.png)

### Video
[![Video](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/videothumbnail.png)](https://vimeo.com/285134305)

