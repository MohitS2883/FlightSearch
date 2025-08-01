package network

import SerpApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApiService {
    @GET("search")
    suspend fun searchFlights(
        @Query("api_key") apiKey: String = "",
        @Query("engine") engine: String = "google_flights",
        @Query("departure_id") departureId: String,
        @Query("arrival_id") arrivalId: String,
        @Query("outbound_date") outboundDate: String,
        @Query("return_date") returnDate: String? = null,
        @Query("adults") adults: Int = 1,
        @Query("currency") currency: String = "INR",
        @Query("type") type: Int = 2
    ): SerpApiResponse
}
