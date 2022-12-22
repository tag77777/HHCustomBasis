package a77777_888.me.t.https.hhcustombasis.ui.splash

import a77777_888.me.t.https.hhcustombasis.ui.base.BaseViewModel
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sourceProvider: SourceProvider
) : BaseViewModel() {

    fun getAreas() {
        resultFlow.value = PendingResult()

        viewModelScope.safeLaunch {
            resultFlow.value = sourceProvider.getAreas()
        }
    }
}