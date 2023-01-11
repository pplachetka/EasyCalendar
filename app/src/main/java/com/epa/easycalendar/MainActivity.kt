package com.epa.easycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epa.easycalendar.ui.theme.EasyCalendarTheme
import com.epa.easycalendar.util.DateUtil
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
               //     Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun EasyCalendarFull(
    modifier: Modifier,
    state: CalendarState,
    maxYearsForward: Int = 0,
    maxYearsBackward: Int = 0,
    ) 
{
    var inMonthSelect by remember { mutableStateOf(false) }
    var inYearSelect by remember { mutableStateOf(false) }

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    val currentDayOfMonth = currentDate.dayOfMonth

    var selectedMonth = state.getSelectedMonth()
    var selectedYear = state.getSelectedYear()


    BoxWithConstraints(modifier) {

        val calendarWidth = this.maxWidth
        val calendarHeight = this.maxHeight

        Column(
            Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val monthRotationAnimation by animateFloatAsState(
                    targetValue = if(inMonthSelect) 180f else 0f,
                    animationSpec = tween(200)
                )
                val yearRotationAnimation by animateFloatAsState(
                    targetValue = if(inYearSelect) 180f else 0f,
                    animationSpec = tween(200)
                )

                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(25))
                        .background(Color.LightGray),
                    onClick = {
                        if(state.getSelectedMonth() > 1){
                            state.setSelectedMonth(state.getSelectedMonth()-1)
                        }
                        else{
                            state.setSelectedMonth(12)
                            state.setSelectedYear(state.getSelectedYear()-1)
                        }
                    })
                {
                    Icon(painter = painterResource(id = R.drawable.ic_before), contentDescription = "backButton")
                }

                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            if(inYearSelect) inYearSelect = false
                            inMonthSelect = !inMonthSelect
                        }
                    ) {
                        Text(text = DateUtil.matchMonthFromInt(selectedMonth))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_drop_down),
                            contentDescription = "dropdownMonth",
                            modifier = Modifier.rotate(monthRotationAnimation)
                        )
                    }


                    Spacer(modifier = Modifier.size(width = 32.dp, height = 0.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            if(inMonthSelect) inMonthSelect = false
                            inYearSelect = !inYearSelect
                        }
                    ) {
                        Text(text = selectedYear.toString())
                        Icon(
                            painter = painterResource(id = R.drawable.ic_drop_down),
                            contentDescription = "dropdownYear",
                            modifier = Modifier.rotate(yearRotationAnimation)
                        )
                    }
                }


                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(25))
                        .background(Color.LightGray),
                    onClick = {
                        if(state.getSelectedMonth() < 12){
                            state.setSelectedMonth(state.getSelectedMonth()+1)
                        }
                        else{
                            state.setSelectedMonth(1)
                            state.setSelectedYear(state.getSelectedYear()+1)
                        }
                    })
                {
                    Icon(painter = painterResource(id = R.drawable.ic_next), contentDescription = "forwardButton")
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for(weekDay in DateUtil.weekDaysShort){
                    Text(
                        text = weekDay,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                userScrollEnabled = false
            ) {
                items(DateUtil.createMonthGrid(selectedMonth, selectedYear)){ monthDay ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(25)),
                        color = if(monthDay == state.getMarkedDate()?.dayOfMonth && monthDay != null) Color.Cyan else Color.Transparent,
                        onClick = {
                            if(monthDay != null){
                                state.setMarkedDate(LocalDate.of(selectedYear, selectedMonth, monthDay))
                                println(state.getMarkedDate().toString())
                            }
                        }
                    ) {
                        if(monthDay != null){
                            Text(
                                text = monthDay.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }


    }
    

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasyCalendarTheme {
        val calendarState = rememberCalendarState()

        EasyCalendarFull(modifier = Modifier, calendarState)
    }
}