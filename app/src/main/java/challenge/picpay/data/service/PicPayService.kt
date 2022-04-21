package challenge.picpay.data.service

import challenge.picpay.data.model.User
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<User>
}
