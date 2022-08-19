package a77777_888.me.t.https.hhcustombasis.fragments.adapters

import a77777_888.me.t.https.hhcustombasis.databinding.DefaultLoadStateBinding
import a77777_888.me.t.https.hhcustombasis.source.EmptyListException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

interface DefaultLoadStateAdapterNavigator {
    fun tryAgain()
    fun goToSearch()
}

class DefaultLoadStateAdapter(
    private val navigator: DefaultLoadStateAdapterNavigator
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DefaultLoadStateBinding.inflate(inflater, parent, false)
        return Holder(binding, navigator)
    }

    class Holder(
        private val binding: DefaultLoadStateBinding,
//        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val navigator: DefaultLoadStateAdapterNavigator
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener { navigator.tryAgain() }
            binding.goBackButton.setOnClickListener { navigator.goToSearch() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                messageTextView.isVisible = true
                tryAgainButton.isVisible = true
                goBackButton.isVisible = true

                if (loadState.error is EmptyListException) {
                    tryAgainButton.isVisible = false
                    messageTextView.text = "Нет вакансий, соответствующих параметрам запроса"
                }
            }


        }
    }
}