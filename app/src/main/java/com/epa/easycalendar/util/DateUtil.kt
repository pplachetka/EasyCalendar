package com.epa.easycalendar.util

import java.time.LocalDate
import java.time.temporal.TemporalAdjusters.lastDayOfMonth

object DateUtil {



    val weekDaysShort = listOf<String>(
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat",
        "Sun",
    )

    val weekdaysFull = listOf<String>(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    fun matchMonthFromInt(monthInt: Int): String{
        return when(monthInt){
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10-> "October"
            11-> "November"
            12-> "December"
            else -> {"not defined"}
        }
    }

    fun createMonthGrid(monthAsInt: Int, year: Int): List<Int?>{
        val monthList = mutableListOf<Int?>()
        val lastMonthDay = LocalDate.of(year, monthAsInt, 1).with(lastDayOfMonth()).dayOfMonth
        val firstDayOfWeek = LocalDate.of(year, monthAsInt, 1).dayOfWeek.value

        if(firstDayOfWeek != 1){
            for(i in 0 until firstDayOfWeek-1){
                monthList.add(null)
            }
        }
        for(r in 1..lastMonthDay){
            monthList.add(r)
        }
        return monthList
    }

}