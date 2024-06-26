package dev.mcd.calendar.feature.presentation.calendar.month

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mcd.calendar.R
import dev.mcd.calendar.feature.calendar.domain.entity.MonthDays
import dev.mcd.calendar.feature.calendar.domain.entity.isInMonth
import dev.mcd.calendar.feature.presentation.calendar.month.CalendarMonthViewModel.SideEffect.NavigateCreateEvent
import dev.mcd.calendar.feature.presentation.calendar.month.CalendarMonthViewModel.SideEffect.NavigateToDay
import dev.mcd.calendar.feature.presentation.calendar.month.view.CalendarMonthTopBar
import dev.mcd.calendar.feature.presentation.calendar.month.view.CalendarView
import dev.mcd.calendar.feature.presentation.calendar.month.view.EventCountText
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate

@Composable
fun CalendarMonthScreen(
    viewModel: CalendarMonthViewModel = hiltViewModel(),
    onNavigateCreateEvent: (LocalDate) -> Unit,
    onNavigateDay: (LocalDate) -> Unit,
//    onNavigateExport: () -> Unit,
//    onNavigateImport: () -> Unit,
) {
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is NavigateCreateEvent -> onNavigateCreateEvent(it.date)
            is NavigateToDay -> onNavigateDay(it.date)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CalendarMonthTopBar(
                date = state.calendarDate,
                showReturnToDate = state.currentDate != state.calendarDate,
//                onImportClicked = onNavigateImport,
//                onExportClicked = onNavigateExport,
                onReturnToDateClicked = {
                    viewModel.onResetCalendarDate()
                },
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            if (state.monthDays != null) {
                Calendar(
                    date = state.currentDate,
                    monthDays = state.monthDays!!,
                    events = state.events,
                    onPreviousMonth = { viewModel.onPreviousMonth() },
                    onNextMonth = { viewModel.onNextMonth() },
                    onDateClicked = { date -> viewModel.onDateClicked(date) },
                )
            }
        }
    }
}

@Composable
private fun Calendar(
    date: LocalDate,
    monthDays: MonthDays,
    events: Map<LocalDate, Int>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDateClicked: (LocalDate) -> Unit,
) {
    CalendarView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        monthDays = monthDays,
        date = date,
        onCellClicked = { cellDate -> onDateClicked(cellDate.date) },
        renderCell = { cellDate ->
            events[cellDate.date]?.let { count ->
                EventCountText(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(if (cellDate.isInMonth) 1f else 0.6f),
                    count = count,
                )
            }
        },
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextButton(
            onClick = { onPreviousMonth() },
        ) {
            Text(text = stringResource(id = R.string.calendar_previous_month))
        }
        TextButton(
            onClick = { onNextMonth() },
        ) {
            Text(text = stringResource(id = R.string.calendar_next_month))
        }
    }
}
