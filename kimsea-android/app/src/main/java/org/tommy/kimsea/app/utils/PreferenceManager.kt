package org.tommy.kimsea.app.utils

import android.content.Context
import android.content.SharedPreferences


/**
 *
 * 데이터 저장 및 로드 클래스
 *
 */
object PreferenceManager {
    const val PREFERENCES_NAME = "HOHOCAMPING_V2"
    private const val DEFAULT_VALUE_STRING = ""
    private const val DEFAULT_VALUE_BOOLEAN = false
    private const val DEFAULT_VALUE_INT = -1
    private const val DEFAULT_VALUE_LONG = -1L
    private const val DEFAULT_VALUE_FLOAT = -1f
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    /**
     *
     * String 값 저장
     *
     * @param context
     *
     * @param key
     *
     * @param value
     */
    fun setString(
        context: Context,
        key: String?,
        value: String?
    ) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }


    /**
     *
     * String 값 로드
     *
     * @param context
     *
     * @param key
     *
     * @return
     */
    fun getString(context: Context, key: String?): String? {
        val prefs = getPreferences(context)
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    /**
     *
     * 키 값 삭제
     *
     * @param context
     *
     * @param key
     */
    fun removeKey(context: Context, key: String?) {
        val prefs = getPreferences(context)
        val edit = prefs.edit()
        edit.remove(key)
        edit.commit()
    }

    /**
     *
     * 모든 저장 데이터 삭제
     *
     * @param context
     */
    fun clear(context: Context) {
        val prefs = getPreferences(context)
        val edit = prefs.edit()
        edit.clear()
        edit.commit()
    }
}