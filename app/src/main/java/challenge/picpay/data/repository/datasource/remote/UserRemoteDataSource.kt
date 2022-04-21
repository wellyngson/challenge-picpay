package challenge.picpay.data.repository.datasource.remote

import challenge.picpay.data.model.User

interface UserRemoteDataSource {

    suspend fun getUsersRemoteDataSource(): List<User>
}
