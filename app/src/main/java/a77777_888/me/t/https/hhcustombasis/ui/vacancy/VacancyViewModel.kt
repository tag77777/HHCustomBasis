package a77777_888.me.t.https.hhcustombasis.ui.vacancy

import a77777_888.me.t.https.hhcustombasis.ui.base.BaseViewModel
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VacancyViewModel @Inject constructor(
    private val sourceProvider: SourceProvider
) : BaseViewModel() {

    fun getVacancy(idVacancy: String) {
        resultFlow.value = PendingResult()

        viewModelScope.safeLaunch {
            resultFlow.value = sourceProvider.getVacancy(idVacancy)
        }
    }
}