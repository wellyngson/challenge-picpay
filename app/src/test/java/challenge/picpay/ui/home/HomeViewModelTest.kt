package challenge.picpay.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import challenge.picpay.data.model.UserState
import challenge.picpay.data.repository.UserRepository
import challenge.picpay.utils.Utils
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun setupHomeViewModel() = HomeViewModel(
        userRepository,
        testDispatcher
    )

    @Test
    fun userRepositoryGetAllUserShouldCalled_when_HomeViewModelGetUsersIsCalled() =
        runTest(testDispatcher) {
            Dispatchers.setMain(testDispatcher)

            // prepare
            val viewModel = setupHomeViewModel()
            val listUser = Utils.generateListUser()
            val userStateSuccess = UserState.Loaded(listUser)

            // when
            coEvery { userRepository.getAllUser() } returns userStateSuccess
            viewModel.getUsers()

            // then
            coVerify { userRepository.getAllUser() }

            Dispatchers.resetMain()
        }

    @Test
    fun homeViewModelUsersIsFailed_when_UserRepositoryGetAllUser_return_UserStateSuccess() =
        runTest(testDispatcher) {
            Dispatchers.setMain(testDispatcher)

            // prepare
            val viewModel = setupHomeViewModel()
            val listUsers = Utils.generateListUser()
            val usersLoaded = UserState.Loaded(listUsers)

            // when
            coEvery { userRepository.getAllUser() } returns usersLoaded
            viewModel.getUsers()

            // then
            assertThat(viewModel.users.value).isEqualTo(usersLoaded)

            Dispatchers.resetMain()
        }

    @Test
    fun homeViewModelUsersIsFailed_when_UserRepositoryGetAllUser_return_UserStateFailed() =
        runTest(testDispatcher) {
            Dispatchers.setMain(testDispatcher)

            // prepare
            val viewModel = setupHomeViewModel()
            val exception = Exception()
            val listUsers = Utils.generateListUser()
            val usersFailed = UserState.Failed(listUsers, exception)

            // when
            coEvery { userRepository.getAllUser() } returns usersFailed
            viewModel.getUsers()

            // then
            assertThat(viewModel.users.value).isEqualTo(usersFailed)

            Dispatchers.resetMain()
        }
}
