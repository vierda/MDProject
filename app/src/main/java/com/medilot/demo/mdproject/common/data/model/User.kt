package com.medilot.demo.mdproject.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.medilot.demo.mdproject.common.util.Constant

@Entity(tableName = "user")
class User {

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0
    var username : String=""
    var password : String=""
    var role : String = Constant.USER
    var name : String=""

}