package online.afeibaili

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

object Util {
    fun getDateTime(format: String): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
    }

    fun getDateTime(): String {
        return getDateTime("yyyy-MM-dd HH:mm:ss")
    }

    fun exit(message: String) {
        println(message)
        exit()
    }

    fun exit() {
        exitProcess(0)
    }
}