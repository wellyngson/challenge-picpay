package challenge.picpay.data.repository

import challenge.picpay.data.datasource.local.UserLocalDataSource
import challenge.picpay.data.datasource.remote.UserRemoteDataSource
import challenge.picpay.domain.model.User
import challenge.picpay.utils.CacheExtensions.Companion.shouldGetDataInCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : UserRepository {

    override fun getAllUser(): Flow<List<User>> = flow {
        if (shouldGetDataInCache() && getListUserInLocalDataSource().isNotEmpty()) {
            emit(getListUserInLocalDataSource())
        } else {
            remoteDataSource.getUsersRemoteDataSource().collect {
                addOrUpdateInDataBase(it)

                emit(it)
            }
        }
    }.flowOn(dispatcher)

    private fun addOrUpdateInDataBase(listUser: List<User>) {
        listUser.forEach {
            addOrUpdateUsersInLocalDataSource(it)
        }
    }

    private fun addOrUpdateUsersInLocalDataSource(user: User) {
        if (getListUserInLocalDataSource().contains(user)) {
            localDataSource.updateUserLocalDataSource(user)
        } else {
            localDataSource.addUserLocalDataSource(user)
        }
    }

    private fun getListUserInLocalDataSource(): List<User> {
        return localDataSource.getUsersLocalDataSource()
    }
}
