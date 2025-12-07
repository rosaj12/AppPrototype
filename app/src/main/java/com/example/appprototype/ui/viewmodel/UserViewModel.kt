package com.example.appprototype.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appprototype.data.model.Resource
import com.example.appprototype.data.model.User
import com.example.appprototype.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing user-related data and business logic
 * This is the ViewModel layer in MVVM architecture
 * Communicates with Repository and exposes data to the View via LiveData
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    
    // LiveData for user list
    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>> = _users
    
    // LiveData for single user operations
    private val _userOperation = MutableLiveData<Resource<String>>()
    val userOperation: LiveData<Resource<String>> = _userOperation
    
    // LiveData for search results
    private val _searchResults = MutableLiveData<Resource<List<User>>>()
    val searchResults: LiveData<Resource<List<User>>> = _searchResults
    
    init {
        loadAllUsers()
    }
    
    /**
     * Load all users from repository
     */
    fun loadAllUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.Loading())
            repository.getAllUsers().collect { result ->
                _users.postValue(result)
            }
        }
    }
    
    /**
     * Insert a new user
     */
    fun insertUser(name: String, email: String, age: Int) {
        viewModelScope.launch {
            _userOperation.postValue(Resource.Loading())
            
            if (name.isBlank() || email.isBlank()) {
                _userOperation.postValue(Resource.Error("Nome e email são obrigatórios"))
                return@launch
            }
            
            if (age <= 0) {
                _userOperation.postValue(Resource.Error("Idade deve ser maior que 0"))
                return@launch
            }
            
            val user = User(name = name, email = email, age = age)
            val result = repository.insertUser(user)
            
            when (result) {
                is Resource.Success -> {
                    _userOperation.postValue(Resource.Success("Usuário inserido com sucesso"))
                    loadAllUsers()
                }
                is Resource.Error -> {
                    _userOperation.postValue(Resource.Error(result.message ?: "Erro desconhecido"))
                }
                else -> {}
            }
        }
    }
    
    /**
     * Update existing user
     */
    fun updateUser(user: User) {
        viewModelScope.launch {
            _userOperation.postValue(Resource.Loading())
            val result = repository.updateUser(user)
            
            when (result) {
                is Resource.Success -> {
                    _userOperation.postValue(Resource.Success("Usuário atualizado com sucesso"))
                    loadAllUsers()
                }
                is Resource.Error -> {
                    _userOperation.postValue(Resource.Error(result.message ?: "Erro desconhecido"))
                }
                else -> {}
            }
        }
    }
    
    /**
     * Delete user
     */
    fun deleteUser(user: User) {
        viewModelScope.launch {
            _userOperation.postValue(Resource.Loading())
            val result = repository.deleteUser(user)
            
            when (result) {
                is Resource.Success -> {
                    _userOperation.postValue(Resource.Success("Usuário deletado com sucesso"))
                    loadAllUsers()
                }
                is Resource.Error -> {
                    _userOperation.postValue(Resource.Error(result.message ?: "Erro desconhecido"))
                }
                else -> {}
            }
        }
    }
    
    /**
     * Search users by name
     */
    fun searchUsers(query: String) {
        viewModelScope.launch {
            _searchResults.postValue(Resource.Loading())
            repository.searchUsers(query).collect { result ->
                _searchResults.postValue(result)
            }
        }
    }
}
