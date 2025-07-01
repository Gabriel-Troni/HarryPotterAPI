package com.example.harrypotterapi.ui.staff

import androidx.lifecycle.*
import com.example.harrypotterapi.model.Staff
import com.example.harrypotterapi.repository.StaffRepository
import kotlinx.coroutines.launch

class StaffViewModel(private val repository: StaffRepository) : ViewModel() {

    private val _matchedStaff = MutableLiveData<List<Staff>>()
    val matchedStaff: LiveData<List<Staff>> = _matchedStaff

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchStaffByName(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getHogwartsStaff()
                if (response.isSuccessful) {
                    val filtered = response.body()?.filter {
                        it.name.contains(query, ignoreCase = true)
                    } ?: emptyList()
                    _matchedStaff.value = filtered
                    if (filtered.isEmpty()) {
                        _error.value = "Nenhum professor encontrado com esse nome"
                    } else {
                        _error.value = null
                    }
                } else {
                    _error.value = "Erro: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Falha na requisição: ${e.message}"
            }
        }
    }
}

class StaffViewModelFactory(private val repository: StaffRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StaffViewModel(repository) as T
    }
}
