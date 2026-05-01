package com.example.miniproject

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkerIntegrationTest {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testDataSyncWorkerSuccess() {
        val worker = TestListenableWorkerBuilder<DataSyncWorker>(context).build()
        runBlocking {
            val result = worker.doWork()
            assert(result is ListenableWorker.Result.Success)
        }
    }
}