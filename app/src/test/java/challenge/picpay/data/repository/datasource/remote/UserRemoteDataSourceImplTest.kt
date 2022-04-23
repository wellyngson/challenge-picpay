package challenge.picpay.data.repository.datasource.remote

import challenge.picpay.data.service.PicPayService
import challenge.picpay.utils.Utils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRemoteDataSourceImplTest {

    @MockK(relaxed = true)
    private lateinit var picPayService: PicPayService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun setupUserRemoteDataSource() = UserRemoteDataSourceImpl(
        picPayService
    )

    @Test
    fun userDaoUpdateCalled_when_UserRemoteDataSourceGetUsersRemoteDataSourceIsCalled() = runTest {
        // prepare
        val userRemoteDataSource = setupUserRemoteDataSource()
        val listUsers = Utils.generateListUser()

        // when
        coEvery { picPayService.getUsers() } returns listUsers
        userRemoteDataSource.getUsersRemoteDataSource()

        // then
        coVerify { picPayService.getUsers() }
    }
}
