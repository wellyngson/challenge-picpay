package challenge.picpay.data.repository.datasource.local

import challenge.picpay.data.dao.UserDao
import challenge.picpay.utils.Utils
import challenge.picpay.utils.toUser
import challenge.picpay.utils.toUserDto
import com.google.common.truth.Truth.assertThat
import io.mockk.* // ktlint-disable no-wildcard-imports
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserLocalDataSourceImplTest {

    @MockK(relaxed = true)
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun setupUserLocalDataSource() = UserLocalDataSourceImpl(
        userDao
    )

    @Test
    fun userLocalDataSourceGetUsersLocalDataSourceChangeListUserDtoToListUser() = runTest {
        // prepare
        val userLocalDataSource = setupUserLocalDataSource()
        val listUserDto = Utils.generateListUserDto()

        // when
        coEvery { userDao.getUsers() } returns listUserDto

        // then
        assertThat(
            userLocalDataSource.getUsersLocalDataSource()
        ).isEqualTo(
            listUserDto.map { it.toUser() }
        )
    }

    @Test
    fun userDaoAddUserDtoCalled_when_UserLocalDataSourceAddUserLocalDataSourceIsCalled() = runTest {
        // prepare
        val userLocalDataSource = setupUserLocalDataSource()
        val user = Utils.generateUser()
        val userDto = user.toUserDto()

        // when
        userLocalDataSource.addUserLocalDataSource(user)

        // then
        coVerify { userDao.addUserDto(userDto) }
    }

    @Test
    fun userDaoUpdateCalled_when_UserLocalDataSourceUpdateUserLocalDataSourceIsCalled() = runTest {
        // prepare
        val userLocalDataSource = setupUserLocalDataSource()
        val user = Utils.generateUser()
        val userDto = user.toUserDto()

        // when
        userLocalDataSource.updateUserLocalDataSource(user)

        // then
        coVerify { userDao.update(userDto) }
    }
}
