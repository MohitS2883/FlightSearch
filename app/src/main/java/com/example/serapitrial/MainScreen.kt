package com.example.serapitrial

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val flightViewModel: FlightViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val uiState = flightViewModel.flightsUIState.collectAsState()

    Scaffold(
        topBar = { FlightTopBar(scrollBehavior, modifier) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                SearchBar(
                    searchAction = { dep, arr, outDate, retDate, type ->
                        coroutineScope.launch {
                            flightViewModel.getFlights(dep, arr, outDate, retDate, type)
                        }
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            when (uiState.value) {
                is FlightsUIState.Loading -> item {
                    LoadingScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                is FlightsUIState.Success -> {
                    items((uiState.value as FlightsUIState.Success).flights) { flight ->
                        FlightCard(flight)
                    }
                }

                is FlightsUIState.Error -> item {
                    Text(
                        text = "Error: ${(uiState as FlightsUIState.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is FlightsUIState.Idle -> item {
                    Text(
                        text = "Idle",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SocialMainScreenPreview() {
    MainScreen()
}