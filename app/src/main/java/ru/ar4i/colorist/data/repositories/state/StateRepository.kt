package ru.ar4i.colorist.data.repositories.state

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.ar4i.colorist.BuildConfig
import java.lang.Exception

class StateRepository(private val context: Context, private val gson: Gson) : IStateRepository {

    companion object {
        val SHARED_PREFS_NAME = BuildConfig.SHARED_PREFS_NAME
        private val POSITION = "position"
        private val SEEK_BARS_VALUES = "seek_bars_values"
        private val DEFAULT_POSITION = 0
        private val DEFAULT_SEEKBARS_STATE = Triple(255, 255, 255)
    }

    override fun saveLastPosition(position: Int) {
        try {
            val sharedPreferences = getSharedPreferences()
            sharedPreferences.edit().putInt(POSITION, position).commit()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun getLastPosition(): Int {
        return try {
            val sharedPreferences = getSharedPreferences()
            sharedPreferences.getInt(POSITION, DEFAULT_POSITION)
        } catch (exception: Exception) {
            exception.printStackTrace()
            DEFAULT_POSITION
        }
    }

    override fun saveLastSeekbarsState(colors: Triple<Int, Int, Int>) {
        try {
            val sharedPreferences = getSharedPreferences()
            sharedPreferences.edit().putString(SEEK_BARS_VALUES, gson.toJson(colors)).commit()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun getLastSeekbarsState(): Triple<Int, Int, Int> {
        return try {
            val sharedPreferences = getSharedPreferences()
            val jsonValue = sharedPreferences.getString(SEEK_BARS_VALUES, "")
            if (!jsonValue.isNullOrEmpty()) {
                val data = gson.fromJson(jsonValue, Triple::class.java)
                data as Triple<Int, Int, Int>
            } else {
                DEFAULT_SEEKBARS_STATE
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            DEFAULT_SEEKBARS_STATE
        }
    }

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }
}