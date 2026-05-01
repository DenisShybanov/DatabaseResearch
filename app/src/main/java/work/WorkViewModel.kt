package com.example.miniproject
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
class WorkViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    // Сценарій A: Базове відображення списку задач
    val allWorkInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("sync_tag")

    // Сценарій A & B: Запуск задачі з тегом для фільтрації
    fun startSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Сценарій C:Оффлайн-режим
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .setConstraints(constraints)
            .addTag("sync_tag") // Тег для фільтрації (Сценарій B)
            .build()

        workManager.enqueueUniqueWork(
            "unique_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    // Сценарій B: Скасування конкретних задач (керування)
    fun cancelWork() {
        workManager.cancelAllWorkByTag("sync_tag")
    }
}