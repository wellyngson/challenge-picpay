package challenge.picpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
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
    }

    private fun setupAdapter() {
        viewBinding.recyclerView.adapter = UserListAdapter()
    }

    private fun setupUsersObserver() {
        viewModel.users.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is UserState.Initial -> {
                }
                is UserState.Loading -> {
                    viewBinding.loading.isVisible = true
                }
                is UserState.Loaded -> {
                    viewBinding.loading.isVisible = false
                    updateAdapter(it.users)
                }
                is UserState.Failed -> {
                    viewBinding.loading.isVisible = false
                }
            }
        }
    }

    private fun updateAdapter(users: List<User>) {
        (viewBinding.recyclerView.adapter as? UserListAdapter)?.submitList(users)
    }

    companion object {
        const val TAG = "com.picpay.desafio.android.ui.home"

        fun newInstance() = HomeFragment()
    }
}
