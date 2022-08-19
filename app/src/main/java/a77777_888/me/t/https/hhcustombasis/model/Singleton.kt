package a77777_888.me.t.https.hhcustombasis.model

import a77777_888.me.t.https.hhcustombasis.fragments.adapters.VacanciesAdapter
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings
import a77777_888.me.t.https.hhcustombasis.source.RetrofitSource
import a77777_888.me.t.https.hhcustombasis.source.RetrofitSourceProvider

object Singleton {

    var vacancies: List<Vacancy>? = null

    var areas: List<Areas>? = null

    var searchSettings: SearchSettings? = null

    val retrofitSourceProvider by lazy {
        RetrofitSourceProvider(RetrofitSource)
    }

    var adapter: VacanciesAdapter? = null

    const val BASE_URL = "https://api.hh.ru"
}