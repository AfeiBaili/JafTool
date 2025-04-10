package online.afeibaili

import online.afeibaili.command.JafCommand
import org.junit.jupiter.api.Test
import java.io.File

class Jaf {
    @Test
    fun testReadLine() {
        var array: Array<String> =
            "add|B:\\Program Files\\Java\\jdk-1.8_421|-i|8".split("|").toTypedArray()

        array =
            "execute 8 command jps".split(" ").toTypedArray()
        JafCommand.parse(array)
    }

    @Test
    fun testFile() {
        var file: File = File("B:\\Program Files\\Java\\jdk-1.8_421")
        if (file.name == "bin") return
        var listFiles = file.listFiles { it.name == "bin" }
        if (listFiles != null && listFiles.isNotEmpty()) {
            println(listFiles[0].path)
        }
    }
}