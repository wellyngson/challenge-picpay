package challenge.picpay.data.repository.datasource.local

import challenge.picpay.data.dao.UserDao
import challenge.picpay.data.model.User
import challenge.picpay.utils.toUser
import challenge.picpay.utils.toUserDto
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override fun getUsersLocalDataSource(): List<User> =
        userDao.getUsers().map { it.toUser() }

    override fun addUserLocalDataSource(user: User) {
        userDao.addUserDto(user.toUserDto())
    }

    override fun updateUserLocalDataSource(user: User) {
        userDao.update(user.toUserDto())
    }
}
