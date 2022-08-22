package a77777_888.me.t.https.hhcustombasis.fragments.initial

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.FragmentInitialBinding
import a77777_888.me.t.https.hhcustombasis.fragments.base.BaseFragment
import a77777_888.me.t.https.hhcustombasis.model.*
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

/**
 * Такая скромная функциональность была выделена в отдельный фрагмент с учетом перспективы
 * дальнейшего расширения. Может понадобиться авторизация, и не только..
 */
@AndroidEntryPoint
class InitialFragment : BaseFragment(R.layout.fragment_initial) {

    private lateinit var binding: FragmentInitialBinding
    override val viewModel: InitialViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override val resultHandler: (result: Result<*>) -> Unit = {
        binding.run {

                when (it) {
                    is EmptyResult -> {}
                    is PendingResult -> {
                        waitLayout.visibility = VISIBLE
                        errorLayout.visibility = INVISIBLE
                    }
                    is SuccessResult -> {
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = INVISIBLE
                        Singleton.areas = it.value as List<Areas>
                        goForward()
                    }
                    is ErrorResult -> {
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = VISIBLE

                        errorTextView.text = "${it.message}\n${it.error}"
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentInitialBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.tryAgainButton.setOnClickListener { viewModel.getAreas() }

        viewModel.getAreas()
    }

    private fun goForward() {
        findNavController().navigate(R.id.action_initialFragment_to_searchFragment)
    }

}