package challenge.picpay.data.repository

import challenge.picpay.data.model.User
import challenge.picpay.data.model.UserState
import challenge.picpay.data.repository.datasource.local.UserLocalDataSource
import challenge.picpay.data.repository.datasource.remote.UserRemoteDataSource
import challenge.picpay.utils.CacheExtensions.Companion.shouldGetDataInCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getAllUser(): UserState = withContext(Dispatchers.IO) {
        try {
            if (!shouldGetDataInCache()) {
                userRemoteDataSource.getUsersRemoteDataSource().map {
                    addOrUpdateUsersInLocalDataSource(it)
                }
            }

            UserState.Loaded(getListUserInLocalDataSource())
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
