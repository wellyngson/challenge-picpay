package challenge.picpay.data.datasource.remote

import challenge.picpay.data.mapper.MapperUserResponseToUser
import challenge.picpay.data.service.PicPayService
import challenge.picpay.domain.model.User
import challenge.picpay.utils.mapToCustomError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRemoteDataSourceImpl(
    private val service: PicPayService
) : UserRemoteDataSource {

    override fun getUsersRemoteDataSource(): Flow<List<User>> = flow {
        emit(
            service.getUsers().map {
                MapperUserResponseToUser().map(it)
            }
        )
    }.mapToCustomError()
}
