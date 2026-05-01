package com.example.miniproject.work

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class DataSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // 1. Отримуємо ConnectivityManager для перевірки мережі
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // 2. Сучасна перевірка наявності інтернету (API 23+)
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val hasInternet = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        // Сценарій C: Обробка відсутності мережі прямо в момент старту
        if (!hasInternet) {
            // Повертаємо retry, щоб WorkManager спробував запустити задачу пізніше
            return Result.retry()
        }

        return try {
            // Сценарій A: Базовий функціонал (імітація завантаження даних)
            // Тут може бути виклик до вашого API або бази даних
            delay(2000)

            // Повертаємо успіх
            Result.success()
        } catch (e: Exception) {
            // Сценарій C: Якщо сталася непередбачувана помилка
            // Можна повернути Result.retry() для повтору або Result.failure() для повної зупинки
            Result.retry()
        }
    }
}