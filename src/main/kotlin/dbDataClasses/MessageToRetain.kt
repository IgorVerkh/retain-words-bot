package dbDataClasses

import java.time.LocalDateTime

data class MessageToRetain(
    val msgText: String,
    val dates: MutableList<LocalDateTime>,
    val _id: String? = null
)
