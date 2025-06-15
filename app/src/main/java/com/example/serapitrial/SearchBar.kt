package com.example.serapitrial

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchAction: (String, String, String, String, Int) -> Unit,
) {
    var from = rememberSaveable { mutableStateOf("") }
    var to = rememberSaveable { mutableStateOf("") }
    val dateState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            TextFieldCustom(
                text = from,
                onChange = { from.value = it },
                direction = "Departure"
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextFieldCustom(
                text = to,
                onChange = { to.value = it },
                direction = "Arrival"
            )
            Spacer(modifier = Modifier.height(8.dp))
            DatePicker(state = dateState)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val departure = from.value
                    val arrival = to.value
                    val outbound = dateState.selectedDateMillis?.let {
                        java.time.Instant.ofEpochMilli(it)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                            .toString()
                    } ?: return@Button

                    searchAction(departure, arrival, outbound, "", 2)
                },
                shape = RoundedCornerShape(2.dp),
                colors = if (isSystemInDarkTheme()) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                },
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .padding(end = 2.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Search")
            }
        }

        IconButton(
            onClick = {
                val temp = from.value
                from.value = to.value
                to.value = temp
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 48.dp)
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF000000)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .offset(y = (-4).dp)
                        .size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .offset(y = (4).dp)
                        .size(16.dp)
                )
            }
        }
    }
}