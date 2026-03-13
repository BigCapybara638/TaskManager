<div align="center">
  <img src="https://github.com/your-username/your-repo/raw/main/compose-multiplatform.svg" alt="Kotlin Multiplatform Logo" width="80" height="80">
  <h1>📋 KMM Task Manager</h1>
  <p>
    <strong>Мультиплатформенный менеджер задач, построенный на Kotlin Multiplatform и Compose Multiplatform.</strong>
  </p>
  <p>
    <img src="https://img.shields.io/badge/platform-iOS%20%7C%20Android%20%7C%20Desktop-brightgreen" alt="Platform: iOS, Android, Desktop">
    <img src="https://img.shields.io/badge/Kotlin-2.0.0-purple?logo=kotlin" alt="Kotlin Version">
    <img src="https://img.shields.io/badge/Compose%20Multiplatform-1.6.0-blue" alt="Compose Version">
  </p>
  <br>
  <img src="https://github.com/your-username/your-repo/raw/main/screenshots/preview.png" alt="App Preview" width="800">
</div>

## ✨ О проекте

**Task Manager** — это демонстрационное приложение, показывающее возможности современной Kotlin Multiplatform разработки. Оно работает на **iOS**, **Android** и **Desktop (macOS/Windows/Linux)** с использованием единой кодовой базы.

Приложение позволяет управлять списком задач с поддержкой темной темы и плавной анимацией.

## 🛠 Технологический стек

*   **Язык:** [Kotlin](https://kotlinlang.org/) Multiplatform
*   **UI:** [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) от JetBrains
*   **Архитектура:** Clean Architecture + MVI
*   **DI:** [Koin](https://insert-koin.io/) (или Kodein-DI)
*   **База данных:** [SQLDelight](https://github.com/cashapp/sqldelight) от Square
*   **Асинхронность:** [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
*   **Навигация:** [Decompose](https://github.com/arkivanov/Decompose) (или Voyager)

## 🚀 Функциональность

-   [x] 📝 Создание, редактирование и удаление задач
-   [x] ✅ Отметка о выполнении
-   [x] 🌗 Автоматическая смена темы (светлая/темная) в зависимости от системы
-   [x] 📱 Адаптивный интерфейс для всех платформ
-   [x] 💾 Локальное хранение данных

## 📸 Скриншоты

<div align="center">
  <table>
    <tr>
      <td><img src="screenshots/android_1.png" alt="Android" width="200"/></td>
      <td><img src="screenshots/ios_1.png" alt="iOS" width="200"/></td>
      <td><img src="screenshots/desktop_1.png" alt="Desktop" width="300"/></td>
    </tr>
    <tr align="center">
      <td>Android</td>
      <td>iOS</td>
      <td>Desktop (macOS)</td>
    </tr>
  </table>
</div>

## 🏁 Запуск проекта

### Предварительные требования
-   Android Studio Koala (или IntelliJ IDEA)
-   Xcode (для запуска на iOS)
-   JDK 17+

### Шаги для запуска

1.  **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/your-username/kmm-task-manager.git
    ```
2.  **Откройте проект** в Android Studio.
3.  **Для Android:** Выберите конфигурацию `androidApp` и нажмите Run.
4.  **Для Desktop:** Выберите конфигурацию `composeApp` (или `desktopApp`) и нажмите Run.
5.  **Для iOS:** Откройте папку `iosApp/iosApp.xcodeproj` в Xcode и соберите проект (или используйте плагин KMM в Android Studio).

## 🤝 Как внести вклад

Вклады приветствуются! Если у вас есть идеи или вы нашли баг, создайте [Issue](https://github.com/your-username/your-repo/issues) или отправьте Pull Request.

1. Форкните проект
2. Создайте ветку для фичи (`git checkout -b feature/amazing-feature`)
3. Закоммитьте изменения (`git commit -m 'Add some amazing feature'`)
4. Запушьте в ветку (`git push origin feature/amazing-feature`)
5. Откройте Pull Request

## 📄 Лицензия

Распространяется под лицензией MIT. Смотрите [LICENSE](LICENSE) для получения дополнительной информации.

## 📬 Контакты

Ваше Имя - [@your_twitter](https://twitter.com/your_twitter) - email@example.com

Ссылка на проект: [https://github.com/your-username/your-repo](https://github.com/your-username/your-repo)

---

<div align="center">
  <sub>Сделано с ❤️ и Kotlin Multiplatform.</sub>
</div>