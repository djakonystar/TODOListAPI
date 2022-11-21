package dev.djakonystar.todolistapi.di

import dev.djakonystar.todolistapi.core.Settings
import dev.djakonystar.todolistapi.data.retrofit.ApiService
import dev.djakonystar.todolistapi.ui.login.LoginViewModel
import dev.djakonystar.todolistapi.ui.me.MeViewModel
import dev.djakonystar.todolistapi.ui.register.RegisterViewModel
import dev.djakonystar.todolistapi.ui.update.UpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<ApiService>() {
        get<Retrofit>(named("HerokuApp")).create(ApiService::class.java)
    }

    single<Retrofit>(named("HerokuApp")) {
        Retrofit.Builder()
            .baseUrl("https://api-nodejs-todolist.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(named("Second")) {
        Retrofit.Builder()
            .baseUrl("https://api-nodejs-todolist.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

val viewModelModule = module {
    viewModel { MeViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { UpdateViewModel(get()) }
}

val settingsModule = module {
    single { Settings(get()) }
}
