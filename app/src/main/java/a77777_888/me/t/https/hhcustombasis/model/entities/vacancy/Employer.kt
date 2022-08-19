package a77777_888.me.t.https.hhcustombasis.model.entities.vacancy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employer(
    val alternate_url: String,
    val id: String,
    val logo_urls: LogoUrls,
    val name: String,
    val trusted: Boolean,
    val url: String,
    val vacancies_url: String
)