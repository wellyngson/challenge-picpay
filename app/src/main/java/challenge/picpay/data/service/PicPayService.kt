package challenge.picpay.data.service

import challenge.picpay.data.model.UserResponse
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
