package dev.mcd.calendar.feature.calendar.data

import dev.mcd.calendar.feature.calendar.data.data_source.EventsRepositoryImpl
import dev.mcd.calendar.feature.calendar.data.data_source.dao.Events
import dev.mcd.calendar.feature.calendar.data.data_source.mapper.EventEntityMapper
import dev.mcd.calendar.test.feature.calendar.data.database.calendarDatabaseRule
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate
import java.time.LocalTime

@RunWith(RobolectricTestRunner::class)
class EventsRepositoryImplTest {

    @get:Rule
    val database = calendarDatabaseRule()

    private val events: Events by database

    private val mapper = EventEntityMapper()
    private lateinit var repository: EventsRepositoryImpl
    private val testScope = TestScope()

    @Before
    fun setUp() {
        repository = EventsRepositoryImpl(
            events = events,
            mapper = mapper,
            dispatcher = testScope.coroutineContext[CoroutineDispatcher]!!,
        )
    }

    @Test
    fun `Insert event`() = testScope.runTest {
        repository.addEvent(
            title = "Hello",
            description = "Description",
            date = LocalDate.now(),
            time = LocalTime.now(),
        )
    }

    @Test
    fun `Query event for date`() = testScope.runTest {
        val date = LocalDate.of(2023, 7, 26)

        repository.addEvent(
            title = "Hello",
            description = "Description",
            date = date,
            time = LocalTime.now(),
        )

        repository.findByDate(date).first().title shouldBe "Hello"
    }

    @Test
    fun `Query event for ID`() = testScope.runTest {
        val date = LocalDate.of(2023, 7, 26)

        val entity = repository.addEvent(
            title = "Hello",
            description = "Description",
            date = date,
            time = LocalTime.now(),
        )

        repository.findById(entity.id) shouldBe entity
    }

    @Test
    fun `Query event count for date`() = testScope.runTest {
        val date = LocalDate.of(2023, 7, 26)

        repository.addEvent(
            title = "Hello",
            description = "Description",
            date = date,
            time = LocalTime.now(),
        )

        repository.eventCount(date) shouldBe 1
    }

    @Test
    fun `Update entity`() = testScope.runTest {
        val date = LocalDate.of(2023, 7, 26)

        repository.addEvent(
            title = "Hello",
            description = "Description",
            date = date,
            time = LocalTime.now(),
        )

        val event = repository.findByDate(date).first()
        val update = event.copy(title = "world")

        repository.updateEvent(update)
        repository.findByDate(date).first().title shouldBe "world"
    }

    @Test
    fun `Delete entity by ID`() = testScope.runTest {
        val date = LocalDate.now()

        val entity = repository.addEvent(
            title = "Hello",
            description = "Description",
            date = date,
            time = LocalTime.now(),
        )

        repository.deleteEvent(entity.id)
        shouldThrow<NullPointerException> { repository.findById(entity.id) }
    }
}
