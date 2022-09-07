package challenge.picpay.data.datasource.remote

import challenge.picpay.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    fun getUsersRemoteDataSource(): Flow<List<User>>
}
