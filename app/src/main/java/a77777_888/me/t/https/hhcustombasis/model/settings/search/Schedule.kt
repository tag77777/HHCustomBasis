package a77777_888.me.t.https.hhcustombasis.model.settings.search

data class Schedule(
    val remote: Boolean,
    val fullDay: Boolean,
    val shift: Boolean, // Сменный график
    val flexible: Boolean,  // Гибкий график
    val flyInFlyOut: Boolean  // Вахта
)
