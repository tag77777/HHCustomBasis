package a77777_888.me.t.https.hhcustombasis.ui.splash

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.FragmentSplashBinding
import a77777_888.me.t.https.hhcustombasis.model.*
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.source.AreasProvider
import a77777_888.me.t.https.hhcustombasis.ui.base.BaseFragment
import a77777_888.me.t.https.hhcustombasis.utils.setToolBarColor
import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    override val viewModel: SplashViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override val resultHandler: (result: Result<out Any>) -> Unit = {
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
                        AreasProvider.areas = it.value as List<Areas>
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

        binding = FragmentSplashBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setToolBarColor(R.color.black)

        binding.tryAgainButton.setOnClickListener { viewModel.getAreas() }

        animation()
    }

    private fun animation() {
        with(binding) {
            layout.postDelayed(
                {
                    val transition = Fade().apply { duration = 1500 }
                    TransitionManager.beginDelayedTransition(layout, transition)
                    hhTextView.visibility = VISIBLE
                }, 1000
            )
            layout.postDelayed(
                {
                    val transition = Fade().apply {
                        duration = 1000
                        addListener( object : Transition.TransitionListener{
                            override fun onTransitionEnd(p0: Transition?) {
                                viewModel.getAreas()
                            }

                            override fun onTransitionStart(p0: Transition?) {}
                            override fun onTransitionCancel(p0: Transition?) {}
                            override fun onTransitionPause(p0: Transition?) {}
                            override fun onTransitionResume(p0: Transition?) {}
                        })
                    }

                    TransitionManager.beginDelayedTransition(layout, transition)
                    customBasisTextView.visibility = VISIBLE
                }, 2700
            )
        }
    }

    private fun goForward() {
        with(binding){
            layout.postDelayed(
                {
                    val transition = Fade().apply { duration = 1000 }
                    TransitionManager.beginDelayedTransition(layout, transition)
                    layout.visibility = INVISIBLE
                }, 1000
            )
            layout.postDelayed(
                {
                    (requireActivity() as AppCompatActivity).setToolBarColor(R.color.red)
                    findNavController().navigate(R.id.action_initialFragment_to_searchFragment)
                }, 2500
            )
        }


    }

}