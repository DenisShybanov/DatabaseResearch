1. Опис бібліотеки (WorkManager)
WorkManager — це частина Android Jetpack, призначена для планування фонових завдань, які мають бути виконані гарантовано, навіть якщо додаток перезавантажено або систему перезапущено.
Призначення: Синхронізація даних, резервне копіювання, обробка медіафайлів.
Гарантія виконання: Враховує стан батареї, мережі та інші обмеження (Constraints).
Типи завдань: Одноразові (OneTimeWorkRequest) або періодичні (PeriodicWorkRequest).
2. Інструкція зі встановлення
Додайте наступні залежності у ваш файл build.gradle.kts (Module :app):

Kotlin
dependencies {
    val work_version = "2.9.0"

    // Основна бібліотека WorkManager (Kotlin + coroutines)
    implementation("androidx.work:work-runtime-ktx:$work_version")

    // Для модульного тестування (Unit Testing)
    testImplementation("junit:junit:4.13.2")
    
    // Для інтеграційного тестування WorkManager
    androidTestImplementation("androidx.work:work-testing:$work_version")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}
3. Приклади коду з поясненнями
А. Створення Worker (Бізнес-логіка)
Це клас, який описує, що саме ми робимо у фоні.

Kotlin
package com.example.miniproject.work

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class DataSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Перевірка наявності інтернету (Сценарій C: Edge Case)
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val hasInternet = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!hasInternet) return Result.retry() // Спробувати пізніше

        return try {
            delay(2000) // Імітація завантаження
            Result.success() // Завдання виконано успішно
        } catch (e: Exception) {
            Result.retry() // Помилка — повторити спробу
        }
    }
}
Б. ViewModel (Керування та фільтрація)
Тут ми налаштовуємо умови запуску та відстежуємо статус (Сценарії A та B).

Kotlin
class WorkViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    // Отримання списку задач за тегом (Сценарій B: Фільтрація)
    val allWorkInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("sync_tag")

    fun startSyncWork() {
        // Обмеження (Сценарій C: Офлайн-режим та батарея)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .setConstraints(constraints)
            .addTag("sync_tag") // Тег для керування
            .build()

        // Унікальна черга: замінює попередню задачу новою
        workManager.enqueueUniqueWork("unique_sync", ExistingWorkPolicy.REPLACE, syncRequest)
    }
}
4. Результати вимірювань та профілювання
Згідно з вимогами до тестування та профілювання проєкту:
Unit-тести: Реалізовано 5 тестів для SyncDataProcessor, що перевіряють валідацію даних, формування URL та розрахунок затримок.
Інтеграційні тести: Використовується TestListenableWorkerBuilder для перевірки успішності виконання DataSyncWorker у середовищі Android.
Виміри (Profiling):
RAM: Невеликий сплеск під час активації Worker.
APK Size: Бібліотека WorkManager додає приблизно 50-100 КБ до фінального розміру.
CPU: Навантаження мінімальне, оскільки більшість часу займає очікування (delay).
