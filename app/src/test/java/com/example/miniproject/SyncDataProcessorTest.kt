package com.example.miniproject
import com.example.miniproject.work.SyncDataProcessor
import org.junit.Test // ОБОВ'ЯЗКОВИЙ ІМПОРТ
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals

class SyncDataProcessorTest {
    private val processor = SyncDataProcessor()

    @Test
    fun `isValidData returns false for empty string`() {
        assert(!processor.isValidData(""))
    }

    @Test
    fun `isValidData returns true for valid string`() {
        assert(processor.isValidData("Some data"))
    }

    @Test
    fun `formatSyncUrl builds correct string`() {
        val result = processor.formatSyncUrl("https://api.com", 5)
        assert(result == "https://api.com/sync/5")
    }

    @Test
    fun `calculateRetryDelay returns linear growth`() {
        assert(processor.calculateRetryDelay(3) == 3000L)
    }

    @Test
    fun `isValidData returns false for null`() {
        assert(!processor.isValidData(null))
    }
}