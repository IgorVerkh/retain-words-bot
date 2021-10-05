package helpers


fun removeBotMention(message: String): String {

    // removes /command or /command@bot_name from a message including first space symbol
    val firstSpaceIndex = message.trim().indexOf(char = ' ')

    if (firstSpaceIndex == -1) {
        return ""
    }

    return message.substring(startIndex = firstSpaceIndex + 1)
}

fun createAnswerMsg(msg: String): String {
    // generate message to answer for the /mem command
    return "Your message: \n\n" +
            "$msg \n\n" +
            "...will be reminded to you in 1, 3, 10 and 30 days"
}
