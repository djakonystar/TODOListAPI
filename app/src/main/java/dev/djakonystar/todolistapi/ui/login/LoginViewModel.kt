package dev.djakonystar.todolistapi.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.todolistapi.core.NetworkResult
import dev.djakonystar.todolistapi.core.loginRequest
import dev.djakonystar.todolistapi.core.loginResponse
import dev.djakonystar.todolistapi.data.retrofit.ApiService
import kotlinx.coroutines.launch

class LoginViewModel(private val apiService: ApiService): ViewModel() {
    private val _login: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val login: LiveData<NetworkResult<loginResponse>> = _login

    fun login(user: loginRequest) {
        _login.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.login(user)

                if (response.isSuccessful) {
                    response.body()?.let { login1: loginResponse ->
                        _login.value = NetworkResult.Success(login1)
                    }
                } else {
                    _login.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _login.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}
