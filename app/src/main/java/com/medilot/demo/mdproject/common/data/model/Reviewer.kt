package com.medilot.demo.mdproject.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.medilot.demo.mdproject.common.util.Constant

@Entity(tableName = "reviewer")
class Reviewer {

    @PrimaryKey
    lateinit var reviewerId: String
    var imageId: String=""
    var status: String = Constant.PENDING
    var comments: String = ""
    var name: String = ""
    var timestamp: Long = 0
}