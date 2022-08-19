package a77777_888.me.t.https.hhcustombasis.fragments.employer

import a77777_888.me.t.https.hhcustombasis.fragments.base.BaseViewModel
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.Singleton
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.viewModelScope

class EmployerViewModel(
    private val sourceProvider: SourceProvider = Singleton.retrofitSourceProvider,
) : BaseViewModel() {

    fun getEmployer(idEmployer: String) {
        resultFlow.value = PendingResult<Any>()

        viewModelScope.safeLaunch {
            resultFlow.value = sourceProvider.getEmployer(idEmployer)
        }
    }
}