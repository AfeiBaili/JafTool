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

    fun isExistFormat(format: String): Boolean {
        var list: Array<String> = arrayOf("tar.gz", "rpm", "deb", "rpm", "dmg", "zip", "exe", "msi")
        var isExist: Boolean = list.contains(format)
        if (!isExist) println("不存在的格式请查看支持的格式：${list.joinToString("、")}")
        return isExist
    }
}