<div align="center">
  <h1>📋 KMP Task Manager</h1>
  <p>
    <strong>Мультиплатформенный менеджер задач, построенный на Kotlin Multiplatform и Compose Multiplatform.</strong>
  </p>
  <p>
    <img src="https://img.shields.io/badge/platform-iOS%20%7C%20Android%20%7C%20Desktop-brightgreen" alt="Platform: iOS, Android, Desktop">
    <img src="https://img.shields.io/badge/Kotlin-2.3.0-purple?logo=kotlin" alt="Kotlin Version">
    <img src="https://img.shields.io/badge/Compose%20Multiplatform-1.10.0-blue" alt="Compose Version">
  </p>
  <br>
  <img src="https://github.com/your-username/your-repo/raw/main/screenshots/preview.png" alt="App Preview" width="800">
</div>

## ✨ О проекте

**Task Manager** — это демонстрационное приложение, показывающее возможности современной Kotlin Multiplatform разработки. Оно работает на **iOS**, **Android** и **Desktop (macOS/Windows/Linux)** с использованием единой кодовой базы.

Приложение позволяет управлять списком задач, сортируя их по категориям, а также имеет синхронизацию между устройствами одного аккаунта

## 🛠 Технологический стек

*   **Язык:** [Kotlin](https://kotlinlang.org/) Multiplatform
*   **UI:** [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) от JetBrains
*   **Архитектура:** Clean Architecture + MVVM
*   **DI:** [Koin](https://insert-koin.io/)
*   **База данных:** [PostgreSQL](https://www.postgresql.org/) на [Supabase](https://supabase.com/)
*   **Асинхронность:** [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)

## 🚀 Функциональность

-   [x] 📝 Создание, редактирование и удаление задач
-   [x] ✅ Отметка о выполнении
-   [x] 🌗 Автоматическая смена темы (светлая/темная) в зависимости от системы
-   [x] 📱 Адаптивный интерфейс для всех платформ
-   [x] 💾 Хранение данных на сервере и автоматическое обновление данных на клиентах

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
-   Android Studio (или IntelliJ IDEA)
-   Xcode (для запуска на iOS)
-   JDK 17+

### Шаги для запуска

1.  **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/BigCapybara638/TaskManager.git
    ```
2.  **Откройте проект** в Android Studio.
3.  **Для Android:** Выберите конфигурацию `androidApp` и нажмите Run.
4.  **Для Desktop:** Выберите конфигурацию `composeApp [jvm]` и нажмите Run.
5.  **Для iOS:** Откройте папку `iosApp/iosApp.xcodeproj` в Xcode и соберите проект (или используйте плагин KMM в Android Studio).

## 🤝 Как внести вклад

Вклады приветствуются! Если у вас есть идеи или вы нашли баг, создайте [Issue](https://github.com/BigCapybara638/TaskManager/issues) или отправьте Pull Request.

1. Форкните проект
2. Создайте ветку для фичи (`git checkout -b feature/amazing-feature`)
3. Закоммитьте изменения (`git commit -m 'Add some amazing feature'`)
4. Запушьте в ветку (`git push origin feature/amazing-feature`)
5. Откройте Pull Request

## 📬 Контакты

Павлов Семён - sp2007pavlov@yandex.ru

Ссылка на проект: [https://github.com/BigCapybara638/TaskManager](https://github.com/BigCapybara638/TaskManager)

---

<div align="center">
  <sub>Сделано с ❤️ и Kotlin Multiplatform.</sub>
</div>
