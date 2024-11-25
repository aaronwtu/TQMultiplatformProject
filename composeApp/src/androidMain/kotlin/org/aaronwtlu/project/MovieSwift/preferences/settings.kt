package com.example.common.preferences

import android.content.Context
//import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual fun settings(context: Any?): Settings =
    SharedPreferencesSettings(
        (context as Context).getSharedPreferences(
            "settings",
            Context.MODE_PRIVATE
        )
    )
