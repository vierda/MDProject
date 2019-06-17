package com.medilot.demo.mdproject.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.Util


class ImageConverter {

    private var gson = Util.initGson()

    @TypeConverter
    fun getImages(data: String): ArrayList<Image>?{
        val type = object : TypeToken<ArrayList<Image>>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun getStringFromImages(data: ArrayList<Image>?): String {
        return gson.toJson(data)
    }
}