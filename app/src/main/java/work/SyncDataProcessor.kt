package com.example.miniproject

class SyncDataProcessor {
    fun isValidData(data: String?): Boolean = !data.isNullOrBlank()

    fun formatSyncUrl(base: String, id: Int): String = "$base/sync/$id"

    fun calculateRetryDelay(attempt: Int): Long = (attempt * 1000).toLong()
}