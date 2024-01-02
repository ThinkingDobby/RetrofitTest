package kr.leechiho.retrofittest.data.httpMethod

import kr.leechiho.retrofittest.data.model.AirQualityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityService {
    @GET("nearest_city") // 1)
    fun getAirQualityData(@Query("lat") lat: String, @Query("lon") lon: String,
                          @Query("key") key : String ) : Call<AirQualityResponse>
}