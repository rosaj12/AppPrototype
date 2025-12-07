package com.example.appprototype.data.local

import androidx.room.*
import com.example.appprototype.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for User entity
 * Defines database operations
 */
@Dao
interface UserDao {
    
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
    
    @Query("SELECT * FROM users WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchUsers(searchQuery: String): Flow<List<User>>
}
