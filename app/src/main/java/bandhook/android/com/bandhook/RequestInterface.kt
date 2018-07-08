package bandhook.android.com.bandhook

import retrofit2.Call
import retrofit2.http.GET

interface RequestInterface {

    @GET("json/movies_2017.json")
    fun getBandHookList(): Call<ArrayList<BandHookResponse>>
}