package dev.mcd.calendar.feature.presentation.routing

import java.time.LocalDate

fun LocalDate.navArg() = toString()

fun String.localDateArg() = LocalDate.parse(this)
