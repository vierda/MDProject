package com.medilot.demo.mdproject.common.data.dao

import android.arch.persistence.room.*
import com.medilot.demo.mdproject.common.data.model.Reviewer

@Dao
interface ReviewerDao {

    @Query("SELECT * FROM reviewer")
    fun getAll(): List<Reviewer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reviewer: Reviewer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(reviewer: Reviewer)

    @Query("UPDATE reviewer SET status= :status, comments= :comments, name=:name, timestamp=:timestamp WHERE reviewerId= :id")
    fun updateReviewer(status: String, comments: String, name: String, timestamp: Long, id: Int)

    @Delete
    fun delete(reviewer: Reviewer)
}