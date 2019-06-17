package com.medilot.demo.mdproject.common.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.medilot.demo.mdproject.common.data.converter.ImageConverter
import com.medilot.demo.mdproject.common.data.converter.ReviewerConverter
import com.medilot.demo.mdproject.common.data.dao.ImageDao
import com.medilot.demo.mdproject.common.data.dao.ReviewerDao
import com.medilot.demo.mdproject.common.data.dao.UserDao
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.data.model.Reviewer
import com.medilot.demo.mdproject.common.data.model.User

@Database(entities = [Image::class, Reviewer::class,
    User::class], version = 1, exportSchema = false)
@TypeConverters(ImageConverter::class,ReviewerConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun reviewerDao(): ReviewerDao
    abstract fun userDao(): UserDao
}