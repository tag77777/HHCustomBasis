package a77777_888.me.t.https.hhcustombasis.model.entities.employer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployerResponseEntity(
    val alternate_url: String, // ссылка на представление компании на сайте
    val area: Area, // информация о регионе работодателя
    val branded_description: String?,
    val branding: Any,
    val description: String?, // описание компании в виде строки с кодом HTML
    val id: String,
    val industries: List<Industry>, // Cписок отраслей компании.
    val insider_interviews: List<Interview>, // список интервью или пустой список, если интервью отсутствуют
    val logo_urls: LogoUrls?, // логотипы компании
    val name: String, // название компании
    val open_vacancies: Int, // количество открытых вакансий у работодателя
    val relations: List<Any>, // если работодатель добавлен в черный список, то вернется ['blacklisted'] иначе []
    val site_url: String?, // адрес сайта компании
    val trusted: Boolean, // флаг, показывающий, прошла ли компания проверку на сайте.
    val type: String?, // тип компании (прямой работодатель, кадровое агентство и т.п.)
    val vacancies_url: String // ссылка на поисковую выдачу вакансий данной компании
)