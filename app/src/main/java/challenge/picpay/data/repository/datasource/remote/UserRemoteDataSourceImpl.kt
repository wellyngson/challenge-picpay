package challenge.picpay.data.repository.datasource.remote

import challenge.picpay.data.model.User
import challenge.picpay.data.service.PicPayService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val picPayService: PicPayService
) : UserRemoteDataSource {

    override suspend fun getUsersRemoteDataSource(): List<User> =
        picPayService.getUsers()
}
