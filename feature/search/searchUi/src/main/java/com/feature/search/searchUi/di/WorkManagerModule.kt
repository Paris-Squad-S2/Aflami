package com.feature.search.searchUi.di

import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.repository.search.dataSource.local.WorkManager.ClearMediaWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.dsl.module

val workManagerModule = module {

    single { WorkManager.getInstance(androidContext()) }

    single<WorkerFactory> { KoinWorkerFactory() }

    worker {
        ClearMediaWorker(
            context = androidContext(),
            workerParams = get(),
            mediaLocalDataSource = get()
        )
    }

}
