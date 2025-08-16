# 🎬 Aflami - Your Ultimate Movie & TV Show Companion
# 🎬 أفلامي - مرافقك الأمثل للأفلام والمسلسلات

<div align="center">

![Aflami Logo](https://via.placeholder.com/200x200/FF6B35/FFFFFF?text=🎬+AFLAMI)

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org/)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-blue.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[![MVVM](https://img.shields.io/badge/Pattern-MVVM-orange.svg)](https://developer.android.com/jetpack/guide)

**English**: Discover, explore, and manage your favorite movies and TV shows with Aflami  
**العربية**: اكتشف، واستكشف، ونظم أفلامك ومسلسلاتك المفضلة مع أفلامي

• [🐛 Report Bug](https://github.com/Paris-Squad-S2/Aflami/issues) • [✨ Request Feature](https://github.com/Paris-Squad-S2/Aflami/issues)

</div>

---

## 📱 Screenshots
<div align="center">
  - [Home](https://via.placeholder.com/300x600/FF6B35/FFFFFF?text=🏠+Home)
  - [Search](https://via.placeholder.com/300x600/4ECDC4/FFFFFF?text=🔍+Search)
  - [Details](https://via.placeholder.com/300x600/45B7D1/FFFFFF?text=📱+Details)
  - [Profile](https://via.placeholder.com/300x600/F7DC6F/333333?text=👤+Profile)
  - [Game](https://via.placeholder.com/300x600/BB8FCE/FFFFFF?text=🎮+Game)
</div>

## 🌟 Features
- 🎭 **Comprehensive Entertainment Database** - Browse thousands of movies and TV shows
- 🔍 **Advanced Search & Filtering** - Find content by genre, year, rating, and more
- 📱 **Modern UI/UX** - Beautiful Material Design interface with smooth animations
- 🎯 **Personalized Experience** - Create watchlists and manage your favorites
- 🏠 **Smart Home Screen** - Curated recommendations and trending content
- 🔐 **Secure Authentication** - Safe user account management
- 🎮 **Fun Guessing Games** - Interactive entertainment features
- 📊 **Detailed Media Information** - Comprehensive details about movies and shows
- 🌙 **Dark/Light Theme Support** - Comfortable viewing in any environment
- 📱 **Responsive Design** - Optimized for all screen sizes
- 🌐 **Language Support** - Available in English and Arabic

## 🛠️ Modular Architecture

### 📋 Overview
Aflami is modularized by feature using **Clean Architecture** and **MVVM**. Each module is independent yet integrates via DI.

#### 🏗️ Module Structure

Aflami/
├── 📱 app/
│   └── 🎯 di/                  # Dependency Injection (Hilt setup)
├── 🛠️ buildSrc/                # Build configuration
├── 📊 datasource/
│   ├── 🌐 remote/              # Home, Guess Game, Lists, Category, Search, User
│   └── 💾 local/               # Home, Guess Game, Lists, Category, Search, User
├── 🎨 designsystem/            # UI components and theming
├── 📦 repository/              # Home, Guess Game, Lists, Category, Search, User
├── 🎯 feature/
│   ├── 🏠 home/                # Home screen
│   ├── 🎮 guessGame/           # Guessing game
│   ├── 📋 lists/               # Watchlists
│   ├── 📂 categories/          # Categories
│   ├── 🔍 search/              # Search
│   └── 👤 user/                # User profile
├── 🖼️ safeimageviewer/        # Hide sensitive content
└── 📝 logger/                  # Firebase crash logging


### 🔧 Technologies
- **Kotlin**, **Jetpack**, **Coroutines**
- **Hilt**, **Room**, **Retrofit**
- **Material Design**, **SafeImageViewer**

## 🚀 Getting Started
### Prerequisites
- Android Studio Arctic Fox+
- Kotlin 1.8.0+
- Android SDK 24+
- JDK 11+

### 📥 Installation
1. Clone: `git clone https://github.com/Paris-Squad-S2/Aflami.git`
2. Create `local.properties` with:
   API_TOKEN=your_api_key
   KEYSTORE_PATH=your_keystore_path
   KEYSTORE_PASSWORD=your_keystore_password
   KEY_ALIAS=your_key_alias
   KEY_PASSWORD=your_key_password

3. Sync and run in Android Studio.

## 👥 Team
| Name               | GitHub              |
|--------------------|---------------------|
| Mohamed Elhanafy   | [@Mohamed-Elhanafy] |
| Aziza Helmy        | [@AzizaHelmy]       |
| Haidy AbuGom3a     | [@HaidyAbuGom3a]    |
| Asmaa Karam        | [@Asmaa7071]        |
| Joseph Sameh Fouad | [@Joseph-Sameh-0]   |
| Ahmed Naser        | [@ahmedNaser7]      |
| Renad Alalfy       | [@Renad-Alalfy]     |
| Zeinab             | [@Zeinab979]        |
| Mahmoud Abdelnaby  | [@M-Abdelnabi]      |
| Mustafa Ibrahim    | [@MustafaIbrahim96] |
| Dina Othman        | [@DinaOthman21]     |
| Islam Magdy        | [@IslamMagd]        |
| Yousef Osama Kamal | [@yousef-osama11]   |
| Ahmed Salah        | [@itsahmedsalah]    |
| Muhammed Wael      | [@MuhammedWael9991] |
| Mohammed Al-Akkad  | [@mohammed-akkad]   |

## 📄 Contributing
1. Fork the repo.
2. Create a branch (`git checkout -b feature/name`).
3. Submit a PR.

## 📄 License
[MIT](LICENSE.md)

## 🙏 Acknowledgments
- TMDB, Material Design, Android Team.

---

<div align="center">
[![Email](https://img.shields.io/badge/Email-Contact-red)](mailto:your-email@domain.com)
[![Issues](https://img.shields.io/badge/Issues-Report-orange)](https://github.com/Paris-Squad-S2/Aflami/issues)
⭐ Star this repo!
</div>