package dev.djakonystar.todolistapi.di

import dev.djakonystar.todolistapi.data.retrofit.ApiService
import dev.djakonystar.todolistapi.ui.login.LoginViewModel
import dev.djakonystar.todolistapi.ui.me.MeViewModel
import dev.djakonystar.todolistapi.ui.register.RegisterViewModel
import dev.djakonystar.todolistapi.ui.update.UpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit>(named("Retrofit1")) {
        Retrofit.Builder()
            .baseUrl("https://api-nodejs-todolist.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(named("Retrofit2")) {
        Retrofit.Builder()
            .baseUrl("https://api-nodejs-todolist.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>(named("Retrofit1")).create(ApiService::class.java)
    }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MeViewModel)
    viewModelOf(::UpdateViewModel)
}
