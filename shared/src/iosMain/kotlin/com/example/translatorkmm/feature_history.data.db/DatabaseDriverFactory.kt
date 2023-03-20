package com.example.translatorkmm.feature_history.data.db

import com.example.translatorkmm.database.TranslationDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(
        TranslationDatabase.Schema,
        DATABASE_NAME
    )
}