package com.example.translatorkmm.feature_history.data.db

import android.content.Context
import com.example.translatorkmm.database.TranslationDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(
        TranslationDatabase.Schema,
        context,
        DATABASE_NAME
    )
}