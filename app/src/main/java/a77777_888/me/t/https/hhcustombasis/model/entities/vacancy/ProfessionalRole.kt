package a77777_888.me.t.https.hhcustombasis.model.entities.vacancy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfessionalRole(
    val id: String,
    val name: String
)