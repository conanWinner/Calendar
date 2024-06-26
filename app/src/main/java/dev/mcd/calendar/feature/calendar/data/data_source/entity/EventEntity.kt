package dev.mcd.calendar.feature.calendar.data.data_source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "events",
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime,
)
