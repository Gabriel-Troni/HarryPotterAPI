package com.example.harrypotterapi.ui.character

import androidx.lifecycle.*
import com.example.harrypotterapi.model.Character
import com.example.harrypotterapi.repository.CharacterRepository
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>> = _characterList

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchCharactersByName(name: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                val response = repository.getAllCharacters() // Busca todos os personagens da API
                if (response.isSuccessful) {
                    val allCharacters = response.body() ?: emptyList()
                    val filtered = allCharacters.filter {
                        it.name.contains(name, ignoreCase = true)
                    }
                    if (filtered.isEmpty()) {
                        _error.value = "Nenhum personagem encontrado com '$name'"
                    }
                    _characterList.value = filtered
                } else {
                    _error.value = "Erro na resposta: ${response.code()}"
                    _characterList.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Falha na requisição: ${e.message}"
                _characterList.value = emptyList()
            }
        }
    }
}
