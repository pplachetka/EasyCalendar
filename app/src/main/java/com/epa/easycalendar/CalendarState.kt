package com.epa.easycalendar

import androidx.compose.runtime.*
import java.time.LocalDate

@Composable
fun rememberCalendarState(localDate: LocalDate = LocalDate.now()): CalendarState {
    return remember { CalendarState(localDate) }
}

class CalendarState internal constructor(localDate: LocalDate){
    private val startDate = localDate
    private val _selectedMonth = mutableStateOf(startDate.monthValue)
    private val _selectedYear = mutableStateOf(startDate.year)

    private val selectedMonth = (_selectedMonth as State<Int>)
    private val selectedYear = (_selectedYear as State<Int>)

    private val _markedDate = mutableStateOf<LocalDate?>(null)
    private val markedDate: State<LocalDate?> = _markedDate

    fun getSelectedMonth(): Int = selectedMonth.value
    fun getSelectedYear(): Int = selectedYear.value
    fun setSelectedMonth(month: Int) {_selectedMonth.value = month}
    fun setSelectedYear(year: Int) {_selectedYear.value = year}

    fun getMarkedDate(): LocalDate? = markedDate.value
    fun setMarkedDate(date: LocalDate) {_markedDate.value = date}
}