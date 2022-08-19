package a77777_888.me.t.https.hhcustombasis.model.entities.vacancies

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Snippet(
    val requirement: String?,
    val responsibility: String?
)