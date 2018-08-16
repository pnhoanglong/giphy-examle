# Giphy

**CircleCI build status: **[![CircleCI](https://circleci.com/gh/pnhoanglong/giphy-examle/tree/develop.svg?style=svg)](https://circleci.com/gh/pnhoanglong/giphy-examle/tree/develop)

Giphy is a demo application showing images from [GIPHY](https://giphy.com/). It contains two screens:
* Screen 1:
  * Display lasted  images from Giphy by getting data from Giphy’s Trending API.
  * The images are displayed in still format.
  * An infinite scrolling mechanism is implemented where, whenever the user scrolls  to the bottom of the list, it should be populated with more images.
* Screen 2:
  * Upon tapping on any of the images in Screen 1, this new screen should open up, which displays the animated version of this GIF.
  * This screen should also make use of Giphy’s Random API to automatically load a new random animated GIF (in the file format of your liking) every 10 seconds.
  * Upon tapping back on this view, the user should be taken back to Screen 1.
## How to build
### Requirements
* Android Studio v3.1.3
* Android SDK
### Building configurations
* Gradle 4.4
* Gradle Android Buildtool 3.1.3
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
  * Google Play Service
  * ConstrainLayout for Android
  * Solver for ConstrainLayout
  * Android Support Repository
  * Google Repository

3. Wait for Gradle sync to finish.

4. To run the app, simply click **Run**  ![alt text](https://camo.githubusercontent.com/4b94cee2759faf24f54eedd56b9c725f6f8996f3/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f696d616765732f746f6f6c732f61732d72756e2e706e67). If you are prompted to select a device, choose one where you want the application to be installed. This will build and generate an apk file which will then be installed to your device.
## Application Architecture


## Demo

### Screenshots

![Screen 1](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/screen1.png) ![Screen 2](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/screen2.png)

### Video
[![Video](https://github.com/pnhoanglong/giphy-examle/blob/readme/screenshoot/videothumbnail.png)](https://vimeo.com/285134305)

