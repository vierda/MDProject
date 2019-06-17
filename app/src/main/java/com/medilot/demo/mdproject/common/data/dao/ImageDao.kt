package com.medilot.demo.mdproject.common.data.dao

import android.arch.persistence.room.*
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.data.model.Reviewer

@Dao
interface ImageDao {

    @Query("SELECT * FROM image")
    fun getAll(): List<Image>

    @Query ("SELECT * FROM image WHERE imageId=:imageId")
    fun getImage (imageId:String) : Image

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: ArrayList<Image>)

    @Query ("UPDATE image SET reviewers=:reviewers WHERE imageId=:id")
    fun updateImageReviewers(reviewers:ArrayList<Reviewer>, id:String)

    @Delete
    fun delete(image: Image)
}