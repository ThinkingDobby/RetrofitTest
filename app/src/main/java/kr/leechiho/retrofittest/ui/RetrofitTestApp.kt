package kr.leechiho.retrofittest.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.leechiho.retrofittest.data.RetrofitConnection
import kr.leechiho.retrofittest.data.httpMethod.AirQualityService
import kr.leechiho.retrofittest.data.model.AirQualityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RetrofitTestApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var airQualityData by remember { mutableStateOf<AirQualityResponse?>(null) }

        val retrofitAPI = RetrofitConnection.getInstance().create(AirQualityService::class.java)
        retrofitAPI.getAirQualityData("37.54721983709145", "126.98817468397996", "f823255d-e4ff-4a31-8d7b-13fb8aa63fac")
            .enqueue(object: Callback<AirQualityResponse> {
                override fun onFailure(call: Call<AirQualityResponse>, t: Throwable) {
                    // 실패
                    Log.d("request_status", "failure")
                }

                override fun onResponse(call: Call<AirQualityResponse>, response: Response<AirQualityResponse>) {
                    // 성공
                    Log.d("request_status", "success")

                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let{
                        airQualityData = response.body()
                    }
                }
            })

        airQualityData?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "City: ${data.data.city}")
                Text(text = "State: ${data.data.state}")
                Text(text = "Country: ${data.data.country}")
                Text(text = "Pollution AQI (US): ${data.data.current.pollution.aqius}")
            }
        }
    }
}