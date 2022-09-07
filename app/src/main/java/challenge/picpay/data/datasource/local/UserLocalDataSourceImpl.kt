package challenge.picpay.data.datasource.local

import challenge.picpay.data.dao.UserDao
import challenge.picpay.data.mapper.MapperUserDtoToUser
import challenge.picpay.data.mapper.MapperUserToUserDto
import challenge.picpay.domain.model.User

class UserLocalDataSourceImpl(
    private val dao: UserDao
) : UserLocalDataSource {

    override fun getUsersLocalDataSource(): List<User> =
        dao.getUsers().map {
            MapperUserDtoToUser().map(it)
        }

    override fun addUserLocalDataSource(user: User) {
        dao.addUserDto(MapperUserToUserDto().map(user))
    }

    override fun updateUserLocalDataSource(user: User) {
        dao.update(MapperUserToUserDto().map(user))
    }
}
