package dev.mcd.calendar.feature.presentation.events.create

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.calendar.feature.calendar.domain.EventsRepository
import dev.mcd.calendar.feature.presentation.routing.localDateArg
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<CreateEventViewModel.State, CreateEventViewModel.SideEffect> {

    override val container = container<State, SideEffect>(
        initialState = State(),
        onCreate = {
            val initialDate = savedStateHandle.get<String>("date")?.localDateArg()
            if (initialDate != null) {
                reduce {
                    state.copy(
                        date = initialDate,
                        time = LocalTime.MIDNIGHT,
                    )
                }
            }
        },
    )

    fun onUpdateDate(date: LocalDate) {
        intent {
            val time = state.time ?: LocalTime.MIDNIGHT
            reduce {
                state.copy(
                    date = date,
                    time = time,
                )
            }
        }
    }

    fun onUpdateTime(time: LocalTime) {
        intent {
            reduce {
                state.copy(time = time)
            }
        }
    }

    //tạo sự kiện
    fun onAddEvent(
        title: String,
        description: String?,
    ) {
        intent {
            val date = state.date ?: return@intent
            val time = state.time ?: return@intent

            //Test
            Log.d("TestEvent", date.toString() + " " + time.toString() + " " + title)

            eventsRepository.addEvent(
                title = title,
                description = description ?: "",
                date = date,
                time = time,
            )

            postSideEffect(SideEffect.NavigateBack)
        }
    }

    data class State(
        val date: LocalDate? = null,
        val time: LocalTime? = null,
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
    }
}
