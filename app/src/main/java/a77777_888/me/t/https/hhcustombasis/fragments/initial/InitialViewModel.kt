package a77777_888.me.t.https.hhcustombasis.fragments.initial

import a77777_888.me.t.https.hhcustombasis.fragments.base.BaseViewModel
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.Singleton
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.viewModelScope

class InitialViewModel(
    private val sourceProvider: SourceProvider = Singleton.retrofitSourceProvider,
) : BaseViewModel() {

    fun getAreas() {
        resultFlow.value = PendingResult<Any>()

        viewModelScope.safeLaunch {
            resultFlow.value = sourceProvider.getAreas()
        }
    }
}