package challenge.picpay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import challenge.picpay.R
import challenge.picpay.databinding.FragmentHomeBinding
import challenge.picpay.domain.model.User
import challenge.picpay.ui.model.ViewAction
import challenge.picpay.utils.createDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

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

    private fun updateAdapter(users: List<User>) {
        (viewBinding.recyclerView.adapter as? UserListAdapter)?.submitList(users)
    }

    private fun setupUsersObserver() {
        viewModel.viewAction.observe(viewLifecycleOwner) {
            when (it) {
                is ViewAction.Initial -> {
                }
                is ViewAction.Loading -> {
                    setupLoading()
                }
                is ViewAction.UsersLoaded -> {
                    setupUsersLoaded(it.users)
                }
                is ViewAction.NoConnectionError -> {
                    showNoConnectionError()
                }
                is ViewAction.GenericError -> {
                    showGenericError()
                }
            }
        }
    }

    private fun setupTryAgain() {
        viewBinding.tryAgain.setOnClickListener {
            viewModel.init()
        }
    }

    private fun setupLoading() {
        handleLoading(true)
        handleUsers(false)
    }

    private fun setupUsersLoaded(users: List<User>) {
        updateAdapter(users)
        handleLoading(false)
        handleUsers(true)
    }

    private fun handleLoading(value: Boolean) {
        viewBinding.loading.isVisible = value
    }

    private fun handleUsers(value: Boolean) {
        with(viewBinding) {
            recyclerView.isVisible = value
            tryAgain.isVisible = value
        }
    }

    private fun showNoConnectionError() {
        handleLoading(false)
        showErrorDialog(getString(R.string.error_no_connection_title))
    }

    private fun showGenericError() {
        handleLoading(false)
        showErrorDialog(getString(R.string.error_generic_title))
    }

    private fun showErrorDialog(message: String) {
        context?.createDialog {
            setMessage(message)
            setCancelable(false)
            setPositiveButton(android.R.string.ok) { _, _ -> viewModel.init() }
        }?.show()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
