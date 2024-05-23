package dev.mcd.calendar.feature.calendar.data.data_source.mapper

import dev.mcd.calendar.feature.calendar.data.data_source.entity.EventEntity
import dev.mcd.calendar.feature.calendar.domain.entity.Event

class EventEntityMapper {

    fun toDomain(entity: EventEntity): Event {
        with(entity) {
            return Event(
                id = id!!,
                title = title,
                description = description,
                date = date,
                time = time,
            )
        }
    }

    fun toData(event: Event): EventEntity {
        with(event) {
            return EventEntity(
                id = id,
                title = title,
                description = description,
                date = date,
                time = time,
            )
        }
    }
}
