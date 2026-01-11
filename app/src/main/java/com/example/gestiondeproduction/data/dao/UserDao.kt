package com.example.gestiondeproduction.data.dao

import androidx.room.*
import com.example.gestiondeproduction.data.entity.UserEntity
@Dao
interface UserDao {

    @Insert
    fun insert(user: UserEntity)

    @Query("""
        SELECT * FROM users 
        WHERE username = :username AND password = :password
        LIMIT 1
    """)
    fun login(username: String, password: String): UserEntity?
}
