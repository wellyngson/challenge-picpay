package challenge.picpay.data.repository

import challenge.picpay.data.datasource.local.UserLocalDataSource
import challenge.picpay.data.datasource.remote.UserRemoteDataSource
import challenge.picpay.data.model.UserState
import challenge.picpay.utils.CacheExtensions
import challenge.picpay.utils.Utils
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var userLocalDataSource: UserLocalDataSource

    @MockK(relaxed = true)
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    private fun setupUserRepository(
        spyOnIt: Boolean = false
    ) = UserRepositoryImpl(
        userRemoteDataSource,
        userLocalDataSource,
        testDispatcher
    ).let {
        if (spyOnIt) {
            spyk(it)
        } else {
            it
        }
    }

    @Test
    fun userRemoteDataSourceGetUsersRemoteDataSourceShouldCalled_when_userRepositoryGetAllUserIsCalled() =
        runTest(testDispatcher) {
            // prepare
            val userRepository = setupUserRepository()

            // when
            mockkObject(CacheExtensions)
            every { CacheExtensions.shouldGetDataInCache() } returns false
            userRepository.getAllUser()

            // the
            coVerify { userRemoteDataSource.getUsersRemoteDataSource() }
        }

    @Test
    fun userRepositoryGetAllUsersShould_return_UserStateLoaded_when_GetListUsersInAPI() =
        runTest(testDispatcher) {
            // prepare
            val userRepository = setupUserRepository()
            val users = Utils.generateListUser()
            val userStateLoaded = UserState.Loaded(users)

            // when
            mockkObject(CacheExtensions)
            every { CacheExtensions.shouldGetDataInCache() } returns false
            coEvery {
                userRemoteDataSource.getUsersRemoteDataSource()
            } returns users
            coEvery {
                userLocalDataSource.getUsersLocalDataSource()
            } returns users

            // then
            assertThat(userRepository.getAllUser()).isEqualTo(userStateLoaded)
        }

    @Test
    fun userRepositoryGetAllUsersShould_return_UserStateLoaded_when_GetListUsersInCache() =
        runTest(testDispatcher) {
            // prepare
            val userRepository = setupUserRepository()
            val users = Utils.generateListUser()

            // when
            mockkObject(CacheExtensions)
            every { CacheExtensions.shouldGetDataInCache() } returns true
            coEvery {
                userLocalDataSource.getUsersLocalDataSource()
            } returns users

            // then
            assertThat(userRepository.getAllUser()).isInstanceOf(UserState.Loaded::class.java)
        }

    @Test
    fun userRepositoryGetAllUsersShould_return_UserStateFailed_when_GetListUsersInAPIAndReturnAException() =
        runTest(testDispatcher) {
            // prepare
            val userRepository = setupUserRepository()
            val users = Utils.generateListUser()

            // when
            mockkObject(CacheExtensions)
            every { CacheExtensions.shouldGetDataInCache() } returns false
            coEvery {
                userRemoteDataSource.getUsersRemoteDataSource()
            } throws Exception()
            coEvery {
                userLocalDataSource.getUsersLocalDataSource()
            } returns users

            // then
            assertThat(userRepository.getAllUser()).isInstanceOf(UserState.Failed::class.java)
        }
}
