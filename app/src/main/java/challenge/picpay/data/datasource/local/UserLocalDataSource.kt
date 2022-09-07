package challenge.picpay.data.datasource.local

import challenge.picpay.domain.model.User

interface UserLocalDataSource {
    fun getUsersLocalDataSource(): List<User>
    fun addUserLocalDataSource(user: User)
    fun updateUserLocalDataSource(user: User)
}
