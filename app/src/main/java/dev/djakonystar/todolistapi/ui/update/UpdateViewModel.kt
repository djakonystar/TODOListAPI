package dev.djakonystar.todolistapi.ui.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.todolistapi.core.Constants
import dev.djakonystar.todolistapi.core.NetworkResult
import dev.djakonystar.todolistapi.core.updateRequest
import dev.djakonystar.todolistapi.core.updateResponse
import dev.djakonystar.todolistapi.data.retrofit.ApiService
import kotlinx.coroutines.launch

class UpdateViewModel(private val apiService: ApiService) : ViewModel() {
    private val _update: MutableLiveData<NetworkResult<updateResponse>> = MutableLiveData()
    val update: LiveData<NetworkResult<updateResponse>> = _update

    fun update(user: updateRequest) {
        _update.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.updateMe("Bearer ${Constants.TOKEN}", user)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _update.value = NetworkResult.Success(it)
                    }
                } else {
                    _update.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _update.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}
