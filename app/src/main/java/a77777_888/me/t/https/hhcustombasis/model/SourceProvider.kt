package a77777_888.me.t.https.hhcustombasis.model

import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.model.entities.employer.EmployerResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancy.VacancyResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface SourceProvider {

    suspend fun getAreas(): Result<List<Areas>>
    suspend fun getPagedVacancies(settings: SearchSettings): Flow<PagingData<Vacancy>>
    suspend fun getVacancy(id: String): Result<VacancyResponseEntity>
    suspend fun getEmployer(id: String): Result<EmployerResponseEntity>

}