package com.medilot.demo.mdproject.common.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.medilot.demo.mdproject.common.data.converter.ReviewerConverter

@Entity(tableName="image")
class Image {

    @PrimaryKey
    lateinit var imageId : String

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var imageData: ByteArray? = null

    @TypeConverters(ReviewerConverter::class)
    var reviewers : ArrayList<Reviewer>? = null

}