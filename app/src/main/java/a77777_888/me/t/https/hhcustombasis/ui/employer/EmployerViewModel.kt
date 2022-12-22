package a77777_888.me.t.https.hhcustombasis.ui.employer

import a77777_888.me.t.https.hhcustombasis.ui.base.BaseViewModel
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployerViewModel @Inject constructor(
    private val sourceProvider: SourceProvider
) : BaseViewModel() {

    fun getEmployer(idEmployer: String) {
        resultFlow.value = PendingResult()

        viewModelScope.safeLaunch {
            resultFlow.value = sourceProvider.getEmployer(idEmployer)
        }
    }
}