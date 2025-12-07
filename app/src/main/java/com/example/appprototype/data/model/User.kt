package com.example.appprototype.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a User entity in the database
 * This is the Model layer in MVVM architecture
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val age: Int,
    val isActive: Boolean = true
)
