package a77777_888.me.t.https.hhcustombasis.model.entities.employer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Area(
    val id: String,
    val name: String,
    val url: String // ссылка на информацию о регионе
)