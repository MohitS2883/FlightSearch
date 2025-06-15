import com.google.gson.annotations.SerializedName

data class SerpApiResponse(
    @SerializedName("best_flights")
    val bestFlights: List<FlightResponse>? = null,

    @SerializedName("other_flights")
    val otherFlights: List<FlightResponse>? = null
)

data class FlightResponse(
    val flights: List<Flight>? = null,
    val layovers: List<Layover>? = null,
    @SerializedName("total_duration") val totalDuration: Int? = null,
    @SerializedName("carbon_emissions") val carbonEmissions: CarbonEmissions? = null,
    val price: Int? = null,
    val type: String? = null,
    @SerializedName("airline_logo") val airlineLogo: String? = null,
    @SerializedName("departure_token") val departureToken: String? = null,
    @SerializedName("booking_token")
    val bookingToken: String? = null,
    val extensions: List<String>? = null
)

data class Flight(
    @SerializedName("departure_airport") val departureAirport: Airport? = null,
    @SerializedName("arrival_airport") val arrivalAirport: Airport? = null,
    val duration: Int? = null,
    val airplane: String? = null,
    val airline: String? = null,
    @SerializedName("airline_logo") val airlineLogo: String? = null,
    @SerializedName("travel_class") val travelClass: String? = null,
    @SerializedName("flight_number") val flightNumber: String? = null,
    val extensions: List<String>? = null,
    @SerializedName("ticket_also_sold_by") val ticketAlsoSoldBy: List<String>? = null,
    val legroom: String? = null,
    val overnight: Boolean? = null,
    @SerializedName("often_delayed_by_over_30_min")
    val oftenDelayed: Boolean? = null,
    @SerializedName("plane_and_crew_by")
    val planeAndCrewBy: String? = null
)

data class Airport(
    val name: String? = null,
    val id: String? = null,
    val time: String? = null
)

data class Layover(
    val duration: Int? = null,
    val name: String? = null,
    val id: String? = null,
    val overnight: Boolean? = null
)

data class CarbonEmissions(
    @SerializedName("this_flight") val thisFlight: Int? = null,
    @SerializedName("typical_for_this_route") val typicalForThisRoute: Int? = null,
    @SerializedName("difference_percent") val differencePercent: Int? = null
)