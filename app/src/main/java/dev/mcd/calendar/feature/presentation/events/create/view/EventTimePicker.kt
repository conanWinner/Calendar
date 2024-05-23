package dev.mcd.calendar.feature.presentation.events.create.view

import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import dev.mcd.calendar.feature.presentation.common.date.TimePickerDialog
import java.time.LocalTime

@Composable
fun TimePicker(
    onDismissRequest: () -> Unit,
    onUpdateTime: (LocalTime) -> Unit,
) {
    val timePickerState = rememberTimePickerState(is24Hour = true)

    TimePickerDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            onUpdateTime(
                LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute,
                ),
            )
            onDismissRequest()
        },
    ) {
        TimePicker(state = timePickerState)
    }
}
