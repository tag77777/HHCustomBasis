package a77777_888.me.t.https.hhcustombasis.fragments.base

import a77777_888.me.t.https.hhcustombasis.model.Result
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

//    abstract val errorMessageHandler: (message: String) -> Unit

    abstract val resultHandler: (result: Result<*>) -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessageHandler(it) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resultFlow.collect { resultHandler(it) }
        }


    }
}