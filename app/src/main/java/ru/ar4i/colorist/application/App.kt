package ru.ar4i.colorist.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ar4i.colorist.application.di.AppModule.appModule
import ru.ar4i.colorist.application.di.AppModule.interactorsModule
import ru.ar4i.colorist.application.di.AppModule.presentationModule
import ru.ar4i.colorist.application.di.AppModule.repositoriesModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    repositoriesModule,
                    interactorsModule,
                    presentationModule
                )
            )
        }
    }

}