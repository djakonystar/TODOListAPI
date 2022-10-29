package dev.djakonystar.todolistapi.ui.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.todolistapi.core.Constants
import dev.djakonystar.todolistapi.core.NetworkResult
import dev.djakonystar.todolistapi.data.models.response.User
import dev.djakonystar.todolistapi.data.retrofit.ApiService
import kotlinx.coroutines.launch

class MeViewModel(private val apiService: ApiService): ViewModel() {
    private val _me: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val me: LiveData<NetworkResult<User>> = _me

    fun me() {
        _me.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.me("Bearer ${Constants.TOKEN}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _me.value = NetworkResult.Success(it)
                    }
                } else {
                    _me.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _me.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }

    private val _delete: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val delete: LiveData<NetworkResult<User>> = _delete

    fun deleteMe() {
        _delete.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.deleteMe("Bearer ${Constants.TOKEN}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _delete.value = NetworkResult.Success(it)
                    }
                } else {
                    _delete.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _delete.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}
