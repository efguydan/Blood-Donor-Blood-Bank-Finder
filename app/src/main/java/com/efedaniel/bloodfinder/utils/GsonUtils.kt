package com.efedaniel.bloodfinder.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

class GsonUtils {

    companion object {
        inline fun <reified T> fromJson(json: JsonElement?): T {
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}
