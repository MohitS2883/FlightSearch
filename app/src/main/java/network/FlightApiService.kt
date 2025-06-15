package network

import SerpApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApiService {
    @GET("search")
    suspend fun searchFlights(
        @Query("api_key") apiKey: String = "ddc0f4280053a642f1daab8f427f5402af22b32c7113a9ce69342aee0b98e067",
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