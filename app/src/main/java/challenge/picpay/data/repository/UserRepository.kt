package challenge.picpay.data.repository

import challenge.picpay.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUser(): Flow<List<User>>
}
