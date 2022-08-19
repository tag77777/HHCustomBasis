package a77777_888.me.t.https.hhcustombasis.model.entities.employer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Interview(
    val id: String,
    val title: String,
    val insiderInterview: String // адрес страницы, содержащей интервью
)
