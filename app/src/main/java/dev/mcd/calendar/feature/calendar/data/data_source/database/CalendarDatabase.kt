package dev.mcd.calendar.feature.calendar.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mcd.calendar.feature.calendar.data.data_source.dao.Events
import dev.mcd.calendar.feature.calendar.data.data_source.entity.EventEntity
import dev.mcd.calendar.common.room.converter.LocalDateConverter
import dev.mcd.calendar.common.room.converter.LocalTimeConverter

@Database(
    entities = [
        EventEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    LocalTimeConverter::class,
    LocalDateConverter::class,
)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun events(): Events
}
