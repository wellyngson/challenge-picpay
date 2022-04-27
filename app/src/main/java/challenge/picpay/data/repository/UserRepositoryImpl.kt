package challenge.picpay.data.repository

import challenge.picpay.data.model.User
import challenge.picpay.data.model.UserState
import challenge.picpay.data.repository.datasource.local.UserLocalDataSource
import challenge.picpay.data.repository.datasource.remote.UserRemoteDataSource
import challenge.picpay.di.IoDispatcher
import challenge.picpay.utils.CacheExtensions.Companion.shouldGetDataInCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UserRepository {

    override suspend fun getAllUser(): UserState = withContext(dispatcher) {
        try {
            if (shouldGetDataInCache() && getListUserInLocalDataSource().isNotEmpty()) {
                UserState.Loaded(getListUserInLocalDataSource())
            } else {
                val response = userRemoteDataSource.getUsersRemoteDataSource()

                response.map {
                    addOrUpdateUsersInLocalDataSource(it)
                }

                UserState.Loaded(response)
            }
        } catch (e: Exception) {
            UserState.Failed(getListUserInLocalDataSource(), e)
        }
    }

    private fun addOrUpdateUsersInLocalDataSource(user: User) {
        if (getListUserInLocalDataSource().contains(user)) {
            userLocalDataSource.updateUserLocalDataSource(user)
        } else {
            userLocalDataSource.addUserLocalDataSource(user)
        }
    }

    private fun getListUserInLocalDataSource(): List<User> =
        userLocalDataSource.getUsersLocalDataSource()
}
