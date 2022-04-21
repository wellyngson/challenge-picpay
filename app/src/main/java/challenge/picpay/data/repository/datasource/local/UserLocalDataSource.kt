package challenge.picpay.data.repository.datasource.local

import challenge.picpay.data.model.User

interface UserLocalDataSource {
    fun getUsersLocalDataSource(): List<User>
    fun addUserLocalDataSource(user: User)
    fun updateUserLocalDataSource(user: User)
}
