package com.medilot.demo.mdproject.common.data.dao

import android.arch.persistence.room.*
import com.medilot.demo.mdproject.common.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE username LIKE :username AND password LIKE :password")
    fun getAuthenticationUser(username: String, password: String): User

    @Query("SELECT * FROM user WHERE userId=:userId")
    fun getUser(userId:Int) : User
}