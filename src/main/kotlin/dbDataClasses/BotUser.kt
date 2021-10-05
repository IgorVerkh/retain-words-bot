package dbDataClasses


data class BotUser(
    val id: Long,
    val messages: MutableList<String>
)
