package com.medilot.demo.mdproject.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.medilot.demo.mdproject.common.data.model.Reviewer
import com.medilot.demo.mdproject.common.util.Util

class ReviewerConverter {

    private var gson = Util.initGson()

    @TypeConverter
    fun getReviewers(data: String): ArrayList<Reviewer>?{
        val type = object : TypeToken<ArrayList<Reviewer>>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun getStringFromReviewers(data: ArrayList<Reviewer>?): String {
        return gson.toJson(data)
    }
}