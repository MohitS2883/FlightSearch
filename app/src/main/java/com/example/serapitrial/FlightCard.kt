package com.example.serapitrial

import Airport
import CarbonEmissions
import Flight
import FlightResponse
import Layover
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.serapitrial.ui.theme.PurpleGrey40

@Composable
fun FlightCard(
    flight: FlightResponse
) {
    var expanded = remember { mutableStateOf(false) }
    val difference = flight.carbonEmissions?.differencePercent ?: 0
    val bgColor = if (difference < 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val textColor = if (difference < 0) Color(0xFF2E7D32) else Color(0xFFC62828)
    val departureDateTime = flight.flights?.get(0)?.departureAirport?.time?.split(" ")
    val departureDate = departureDateTime?.getOrNull(0) ?: "--"
    val departureTime = departureDateTime?.getOrNull(1) ?: "--"
    val arrivalDateTime = flight.flights?.lastOrNull()?.arrivalAirport?.time?.split(" ")
    val arrivalDate = arrivalDateTime?.getOrNull(0) ?: "--"
    val arrivalTime = arrivalDateTime?.getOrNull(1) ?: "--"

    Card(
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F4F8)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ).fillMaxWidth()
    ) {
        Column {
            Row {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = flight.airlineLogo,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(8f, false)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .fillMaxWidth()
                    ) {
                        // Departure info
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "$departureDate\n$departureTime",
                                color = Color(0xFF1E88E5),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = flight.flights?.get(0)?.departureAirport?.id ?: "--",
                                color = Color(0xFF1E88E5),
                                fontSize = 16.sp,
                                maxLines = 1
                            )
                        }

                        // Arrow icon
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = PurpleGrey40,
                            modifier = Modifier
                                .weight(0.2f)
                                .size(24.dp)
                                .padding(horizontal = 4.dp)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "$arrivalDate\n$arrivalTime",
                                color = Color(0xFF1E88E5),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = flight.flights?.lastOrNull()?.arrivalAirport?.id ?: "--",
                                color = Color(0xFF1E88E5),
                                fontSize = 16.sp,
                                maxLines = 1
                            )
                        }
                        Text(
                            text = "₹${flight.price}",
                            color = Color(0xFF43A047),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(0.8f)
                                .padding(start = 22.dp, top = 20.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                    ) {
                        Text(
                            text = flight.layovers?.firstOrNull()?.id?.let {
                                "Via $it"
                            } ?: "Direct",
                            color = Color(0xFF1E88E5),
                            fontSize = 12.sp,
                        )
                        VerticalDivider(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .height(12.dp)
                        )
                        Text(
                            text = getDurationInHoursAndMinutes(flight.totalDuration ?: 0),
                            color = Color(0xFF1E88E5),
                            fontSize = 12.sp,
                        )
                        VerticalDivider(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .height(12.dp)
                        )
                        Text(
                            text = "${difference}% emissions",
                            color = textColor,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .background(bgColor)
                                .padding(horizontal = 4.dp)
                        )
                    }

                }
                IconButton(
                    onClick = { expanded.value = !expanded.value },
                    modifier = Modifier.padding(vertical = 28.dp)
                ) {
                    Icon(
                        imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            if (expanded.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Text("Flight Number: ${flight.flights?.get(0)?.flightNumber}")
                    Text("Airline: ${flight.flights?.get(0)?.airline}")
                    Text("Aircraft: ${flight.flights?.get(0)?.airplane}")
                    Text("Class: ${flight.flights?.get(0)?.travelClass}")
                    Text("Legroom: ${flight.flights?.get(0)?.legroom}")
                    Text("Extras: ${flight.flights?.get(0)?.extensions?.joinToString() ?: "None"}")
                    Text("Also sold by: ${flight.flights?.get(0)?.ticketAlsoSoldBy?.joinToString() ?: "N/A"}")
                }
            }
        }
    }
}

private fun getDurationInHoursAndMinutes(duration: Int): String {
    val hours = duration / 60
    val minutes = duration % 60
    return "$hours h $minutes min"
}

internal fun getFlightResponse(): FlightResponse {
    return FlightResponse(
        flights = listOf(
            Flight(
                departureAirport = Airport(
                    name = "Kempegowda International Airport",
                    id = "BLR",
                    time = "2025-06-16 06:30"
                ),
                arrivalAirport = Airport(
                    name = "Indira Gandhi International Airport",
                    id = "DEL",
                    time = "2025-06-16 08:45"
                ),
                duration = 135,
                airplane = "Airbus A320",
                airline = "IndiGo",
                airlineLogo = "https://example.com/logos/indigo.png",
                travelClass = "Economy",
                flightNumber = "6E203",
                extensions = listOf("Wi-Fi", "USB Charging"),
                ticketAlsoSoldBy = listOf("MakeMyTrip", "Cleartrip"),
                legroom = "Standard",
                overnight = false
            )
        ),
        layovers = listOf(
            Layover(
                duration = 45,
                name = "Mumbai Airport",
                id = "BOM"
            )
        ),
        totalDuration = 210,
        carbonEmissions = CarbonEmissions(
            thisFlight = 120,
            typicalForThisRoute = 135,
            differencePercent = -11
        ),
        price = 4654,
        type = "One-way",
        airlineLogo = "https://example.com/logos/indigo.png",
        departureToken = "abc123xyz"
    )
}

@Preview(showBackground = true)
@Composable
fun FlightCardPreview(
    flight: FlightResponse = getFlightResponse(),
) {
    FlightCard(flight)
}