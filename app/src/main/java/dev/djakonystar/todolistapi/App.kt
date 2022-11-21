package dev.djakonystar.todolistapi

import android.app.Application
import dev.djakonystar.todolistapi.di.networkModule
import dev.djakonystar.todolistapi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(networkModule, viewModelModule)
        startKoin {
            androidLogger()

            androidContext(this@App)

            androidFileProperties()

            modules(modules)
        }
    }
}
