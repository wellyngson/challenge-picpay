package challenge.picpay.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import challenge.picpay.R
import challenge.picpay.databinding.ListItemUserBinding
import challenge.picpay.domain.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListAdapter : ListAdapter<User, UserListAdapter.UserListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = ListItemUserBinding.bind(itemView)

        fun bind(user: User) {
            viewBinding.apply {
                name.text = user.name
                username.text = user.username
                progressBar.visibility = View.VISIBLE
                Picasso.get()
                    .load(user.img)
                    .error(R.drawable.ic_round_account_circle)
                    .into(
                        picture,
                        object : Callback {
                            override fun onSuccess() {
                                progressBar.visibility = View.GONE
                            }

                            override fun onError(e: Exception?) {
                                progressBar.visibility = View.GONE
                            }
                        }
                    )
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}
