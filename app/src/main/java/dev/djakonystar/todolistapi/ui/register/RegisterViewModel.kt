package dev.djakonystar.todolistapi.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.todolistapi.core.NetworkResult
import dev.djakonystar.todolistapi.core.loginResponse
import dev.djakonystar.todolistapi.data.models.request.Register
import dev.djakonystar.todolistapi.data.retrofit.ApiService
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiService: ApiService): ViewModel() {
    private val _register: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val register: LiveData<NetworkResult<loginResponse>> = _register

    fun register(user: Register) {
        _register.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(user)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _register.value = NetworkResult.Success(it)
                    }
                } else {
                    _register.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _register.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}
