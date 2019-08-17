package ru.ar4i.colorist.data.repositories.state

import android.content.Context
import android.content.SharedPreferences
import ru.ar4i.colorist.BuildConfig
import java.lang.Exception

class StateRepository(private val context: Context) : IStateRepository {

    companion object {
        val SHARED_PREFS_NAME = BuildConfig.SHARED_PREFS_NAME
        private val POSITION = "position"
        private val DEFAULT_POSITION = 0
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

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }
}