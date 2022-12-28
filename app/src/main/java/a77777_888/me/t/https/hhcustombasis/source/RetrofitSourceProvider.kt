package a77777_888.me.t.https.hhcustombasis.source

import a77777_888.me.t.https.hhcustombasis.model.*
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.model.entities.employer.EmployerResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancy.VacancyResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitSourceProvider
    @Inject constructor(
        retrofit: Retrofit
    ) : SourceProvider {

    override suspend fun getAreas(): Result<List<Areas>> =
            wrapExceptions{
        SuccessResult(sourceAPI.getAreas())
    }

    override suspend fun getPagedVacancies(settings: SearchSettings): Flow<PagingData<Vacancy>> {

        val loader: VacancyPageLoader = { pageIndex, pageSize ->
            getVacancies(settings, pageIndex, pageSize)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { VacanciesPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    override suspend fun getVacancy(id: String): Result<VacancyResponseEntity> = wrapExceptions{
        SuccessResult(sourceAPI.getVacancy(id))
    }

    override suspend fun getEmployer(id: String): Result<EmployerResponseEntity> = wrapExceptions{
        SuccessResult(sourceAPI.getEmployer(id))
    }

    private suspend fun getVacancies(settings: SearchSettings, pageIndex: Int, pageSize: Int)
            : List<Vacancy> = wrapExceptions{

        val localSettings = object : SearchSettings{
            override var findWords = settings.findWords
            override var excludeWords = settings.excludeWords
            override var findIn = settings.findIn
            override var regionId = settings.regionId
            override var experience = settings.experience
            override var schedule = settings.schedule
            override var period = settings.period
        }

        with(localSettings){
            val findWordsString = findWords
            val excludeWordsString = excludeWords
            val nameSearchFieldString = if (findIn.name) "name" else null
            val descriptionSearchFieldString = if (findIn.description) "description" else null
            val companyNameSearchFieldString = if (findIn.companyName) "company_name" else null
            val regionIdString = regionId
            val experienceString = experience
            val remoteScheduleString = if (schedule.remote) "remote" else null
            val fullDayScheduleString = if (schedule.fullDay) "fullDay" else null
            val shiftScheduleString = if (schedule.shift) "shift" else null
            val flexibleScheduleString = if (schedule.flexible) "flexible" else null
            val flyInFlyOutScheduleString = if (schedule.flyInFlyOut) "flyInFlyOut" else null
            val periodString = period

            val response = sourceAPI.getVacancies(
                find = findWordsString,
                exclude = excludeWordsString,
                nameSearchField = nameSearchFieldString,
                descriptionSearchField = descriptionSearchFieldString,
                companyNameSearchField = companyNameSearchFieldString,
                regionId = regionIdString,
                experience = experienceString,
                remoteSchedule = remoteScheduleString,
                fullDaySchedule = fullDayScheduleString,
                shiftSchedule = shiftScheduleString,
                flexibleSchedule = flexibleScheduleString,
                flyInFlyOutSchedule = flyInFlyOutScheduleString,
                period = periodString,
                page = pageIndex,
                pageSize = pageSize
            )

            response.items

        }

    }

    private val sourceAPI: HeadHunterAPI = //retrofitSource.
            retrofit.create(HeadHunterAPI::class.java)

    private suspend fun <T> wrapExceptions(block: suspend () -> T): T {
        // Выполняет блок и бросает исключения:
        // - BackendException с кодом и сообщением, если сервер вернул ошибку
        // - ParseBackendResponseException, если ответ сервера не может быть обработан
        // - ConnectionException, если запрос не выполнен
        return try {
            block()
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
        } catch (e: HttpException) {
            throw BackendException(e.code(), e.message())
        } catch (e: IOException){
            throw ConnectionException(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}
