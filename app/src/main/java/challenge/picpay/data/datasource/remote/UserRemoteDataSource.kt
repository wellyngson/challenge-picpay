package challenge.picpay.data.datasource.remote

import challenge.picpay.data.model.User

interface UserRemoteDataSource {

    suspend fun getUsersRemoteDataSource(): List<User>
}
