package challenge.picpay.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import challenge.picpay.data.repository.UserRepository
import challenge.picpay.ui.model.ViewAction
import challenge.picpay.utils.Utils
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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
            val userStateSuccess = ViewAction.UsersLoaded(listUser)

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
            val usersLoaded = ViewAction.UsersLoaded(listUsers)

            // when
            coEvery { userRepository.getAllUser() } returns usersLoaded
            viewModel.getUsers()

            // then
            assertThat(viewModel.viewAction.value).isEqualTo(usersLoaded)

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
            val usersFailed = ViewAction.Failed(listUsers, exception)

            // when
            coEvery { userRepository.getAllUser() } returns usersFailed
            viewModel.getUsers()

            // then
            assertThat(viewModel.viewAction.value).isEqualTo(usersFailed)

            Dispatchers.resetMain()
        }
}
