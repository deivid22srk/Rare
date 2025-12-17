# Rare Launcher for Android

Android port of the Rare Launcher - An open-source Epic Games Launcher alternative.

## Features

- **Epic Games Authentication**: Secure login via WebView
- **Real Library Access**: View your actual Epic Games library
- **Material Design**: Modern and clean UI
- **Kotlin**: 100% Kotlin codebase

## Requirements

- Android 7.0 (API 24) or higher
- Internet connection

## Building

### Debug Build

```bash
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build

```bash
./gradlew assembleRelease
```

## Installation

1. Download the APK from releases or build it yourself
2. Install on your Android device
3. Grant necessary permissions
4. Login with your Epic Games account
5. View your library!

## Technologies

- **Kotlin**: Primary language
- **Retrofit**: HTTP client for API calls
- **Coroutines**: Asynchronous programming
- **Material Components**: UI components
- **Glide**: Image loading
- **ViewBinding**: View access

## API Endpoints

This app uses the official Epic Games API:
- OAuth: `account-public-service-prod03.ol.epicgames.com`
- Library: `library-service.live.use1a.on.epicgames.com`

## Credits

Based on the [Rare Launcher](https://github.com/RareDevs/Rare) project and [legendary](https://github.com/derrod/legendary) library.

## License

GPL-3.0 License

## Disclaimer

This is an unofficial application and is not affiliated with Epic Games.
