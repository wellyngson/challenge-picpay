package challenge.picpay.data.repository

import challenge.picpay.data.model.UserState

interface UserRepository {

    suspend fun getAllUser(): UserState
}
