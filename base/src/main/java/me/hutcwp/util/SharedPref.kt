package me.hutcwp.util

import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import me.hutcwp.log.MLog
import java.util.*

/**
 * Created by hutcwp on 2019-12-10 20:54
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
open class SharedPref(private val mPref: SharedPreferences) {

    val all: Map<String, *> get() = mPref.all

    fun putString(key: String, value: String) {
        put(key, value)
    }

    fun getString(key: String): String? {
        return get(key)
    }

    fun getString(key: String, defaultValue: String): String? {
        return mPref.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        put(key, value.toString())
    }

    fun putBoolean(key: String, value: Boolean) {
        put(key, value.toString())
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val rawValue = get(key)
        if (TextUtils.isEmpty(rawValue)) {
            return defaultValue
        }
        try {
            return java.lang.Boolean.parseBoolean(rawValue)
        } catch (e: Exception) {
            MLog.error(TAG, "failed to parse boolean value for key %s, %s", key, e)
            return defaultValue
        }

    }

    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        val rawValue = get(key)
        return if (rawValue.isNullOrEmpty()) {
            defaultValue
        } else parseInt(rawValue, defaultValue)
    }

    private fun parseInt(value: String, defaultValue: Int): Int {
        try {
            return Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            MLog.error(TAG, "lcy failed to parse value for key %s, %s", value,
                    e)
            return defaultValue
        }

    }

    fun putLong(key: String, value: Long) {
        put(key, value.toString())
    }

    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        val rawValue = get(key)
        if (TextUtils.isEmpty(rawValue)) {
            return defaultValue
        }
        return try {
            java.lang.Long.parseLong(rawValue)
        } catch (e: NumberFormatException) {
            MLog.error(TAG,
                    "lcy failed to parse %s as long, for key %s, ex : %s",
                    rawValue, key, e)
            defaultValue
        }

    }

    fun putIntArray(key: String, values: Array<Int>) {
        putIntList(key, listOf(*values))
    }

    /**
     * @param key
     * @param outValues For memory reuse, if the result is no greater than this space,
     * will fill into this, the redundant elements won't be touched.
     * If it is null, a new int array will be created if result is
     * not empty.
     * @return The result list, null if no correlated.
     */
    @JvmOverloads
    fun getIntArray(key: String, outValues: IntArray? = null): IntArray? {
        val list = getIntList(key)
        if (list == null || list.isEmpty()) {
            return null
        }

        val ret = if (list.size <= outValues!!.size)
            outValues
        else
            IntArray(list.size)

        var i = 0
        for (e in list) {
            ret[i++] = e
        }
        return ret

    }

    fun putIntList(key: String, values: List<Int>?) {
        if (values == null || values.isEmpty()) {
            return
        }

        val value = TextUtils.join(DELIMITER, values)
        put(key, value)
    }

    fun getIntList(key: String): List<Int>? {
        val `val` = get(key)
        if (TextUtils.isEmpty(`val`)) {
            return null
        }

        val values = TextUtils.split(`val`, DELIMITER)
        if (values == null || values.isEmpty()) {
            return null
        }

        val list = ArrayList<Int>()
        for (value in values) {
            try {
                list.add(Integer.parseInt(value))
            } catch (ex: NumberFormatException) {
                MLog.error(TAG, "lcy failed to parse value for key: $key, value: $value, exception: $ex")
                continue
            }

        }
        return list
    }

    fun putLongList(key: String, values: List<Long>?) {
        if (values == null || values.isEmpty()) {
            return
        }

        val value = TextUtils.join(DELIMITER, values)
        put(key, value)
    }

    fun getLongList(key: String): List<Long>? {
        val `val` = get(key)
        if (TextUtils.isEmpty(`val`)) {
            return null
        }

        val values = TextUtils.split(`val`, DELIMITER)
        if (values == null || values.isEmpty()) {
            return null
        }

        val list = ArrayList<Long>()
        for (value in values) {
            try {
                list.add(java.lang.Long.parseLong(value))
            } catch (ex: NumberFormatException) {
                MLog.error(
                        TAG, "lcy failed to parse value for key: $key, value: $value, exception: $ex")
                continue
            }

        }
        return list
    }

    fun put(key: String, value: String) {
        mPref.edit().putString(key, value).apply()
    }

    operator fun get(key: String): String? {
        return mPref.getString(key, null)
    }

    fun remove(key: String) {
        mPref.edit().remove(key).apply()
    }

    fun clear() {
        mPref.edit().clear().apply()
    }

    fun contain(key: String?): Boolean {
        return if (key == null || key.isEmpty()) {
            false
        } else mPref.contains(key)
    }

    fun putObject(key: String, obj: Any) {
        val gson = Gson()
        val json = gson.toJson(obj)
        put(key, json)
    }

    fun getObj(key: String, className: Class<*>): Any {
        val gson = Gson()
        val json = getString(key, "")
        return gson.fromJson(json, className)
    }

    companion object {
        private const val TAG = "SharedPref"
        private const val DELIMITER = ","
    }

}
