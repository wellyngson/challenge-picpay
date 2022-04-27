package challenge.picpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import challenge.picpay.data.model.User
import challenge.picpay.data.model.UserState
import challenge.picpay.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupUsersObserver()
        setupTryAgain()
    }

    private fun setupAdapter() {
        viewBinding.recyclerView.adapter = UserListAdapter()
    }

    private fun setupUsersObserver() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it) {
                is UserState.Initial -> {
                }
                is UserState.Loading -> {
                    setupUserStateLoading()
                }
                is UserState.Loaded -> {
                    setupUserStateLoaded(it)
                }
                is UserState.Failed -> {
                    setupUserStateFailed(it.users)
                }
            }
        }
    }

    private fun setupUserStateLoading() {
        errorContainerVisibility(false)
        showLoading()
    }

    private fun setupUserStateLoaded(it: UserState.Loaded) {
        dismissLoading()
        updateAdapter(it.users)
    }

    private fun setupUserStateFailed(users: List<User>) {
        errorContainerVisibility(true)
        dismissLoading()

        if (users.isNotEmpty()) {
            errorContainerVisibility(false)
            updateAdapter(users)
        }
    }

    private fun setupTryAgain() {
        viewBinding.tryAgain.setOnClickListener {
            viewModel.getUsers()
        }
    }

    private fun dismissLoading() {
        viewBinding.loading.isVisible = false
    }

    private fun errorContainerVisibility(value: Boolean) {
        viewBinding.errorContainer.isVisible = value
    }

    private fun showLoading() {
        viewBinding.loading.isVisible = true
    }

    private fun updateAdapter(users: List<User>) {
        (viewBinding.recyclerView.adapter as? UserListAdapter)?.submitList(users)
    }

    companion object {
        const val TAG = "com.picpay.desafio.android.ui.home"

        fun newInstance() = HomeFragment()
    }
}
