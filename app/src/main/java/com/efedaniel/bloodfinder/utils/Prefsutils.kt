package com.efedaniel.bloodfinder.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class PrefsUtils @Inject constructor(private val sharedPref: SharedPreferences, private val gson: Gson) {

    fun putString(key: String, value: String?) {
        sharedPref.edit {
            putString(key, value)
        }
    }

    fun doesContain(key: String): Boolean {
        return sharedPref.contains(key)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPref.edit {
            putBoolean(key, value)
        }
    }

    fun putInt(key: String, value: Int) {
        sharedPref.edit {
            putInt(key, value)
        }
    }

    fun putFloat(key: String, value: Float) {
        sharedPref.edit()
                .putFloat(key, value)
                .apply()
    }

    fun putStringSet(key: String, values: Set<String>) {
        sharedPref.edit {
            putStringSet(key, values)
        }
    }

    fun putLong(key: String, value: Long) {
        sharedPref.edit {
            putLong(key, value)
        }
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPref.getFloat(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPref.getLong(key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        return sharedPref.getStringSet(key, defaultValue)
    }

    fun <T> putList(key: String, list: ArrayList<T>) {
        val json = gson.toJson(list)
        putString(key, json)
    }

    fun <T> getList(key: String, type: Type): ArrayList<T> {
        return gson.fromJson<ArrayList<T>>(getString(key, ""), type)
    }

    fun <T> putObject(key: String, targetObject: T) {
        val json = gson.toJson(targetObject)
        putString(key, json)
    }

    fun <T> getPrefAsObject(key: String, clazz: Class<T>): T {
        val json = getString(key, "")
        return gson.fromJson(json, clazz)
    }
}
