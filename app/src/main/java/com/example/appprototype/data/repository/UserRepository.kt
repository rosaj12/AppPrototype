package com.example.appprototype.data.repository

import com.example.appprototype.data.local.UserDao
import com.example.appprototype.data.model.Resource
import com.example.appprototype.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class that abstracts data sources
 * This is the single source of truth for data in MVVM architecture
 * Acts as a mediator between ViewModel and data sources (local DB, remote API)
 */
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    /**
     * Get all users from local database
     */
    fun getAllUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            userDao.getAllUsers().collect { users ->
                emit(Resource.Success(users))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Erro ao carregar usuários"))
        }
    }
    
    /**
     * Get user by ID
     */
    suspend fun getUserById(userId: Int): Resource<User> {
        return try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Usuário não encontrado")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Erro ao buscar usuário")
        }
    }
    
    /**
     * Insert a new user
     */
    suspend fun insertUser(user: User): Resource<Long> {
        return try {
            val id = userDao.insertUser(user)
            Resource.Success(id)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Erro ao inserir usuário")
        }
    }
    
    /**
     * Update existing user
     */
    suspend fun updateUser(user: User): Resource<Unit> {
        return try {
            userDao.updateUser(user)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Erro ao atualizar usuário")
        }
    }
    
    /**
     * Delete user
     */
    suspend fun deleteUser(user: User): Resource<Unit> {
        return try {
            userDao.deleteUser(user)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Erro ao deletar usuário")
        }
    }
    
    /**
     * Search users by name
     */
    fun searchUsers(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            userDao.searchUsers(query).collect { users ->
                emit(Resource.Success(users))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Erro ao pesquisar usuários"))
        }
    }
}
