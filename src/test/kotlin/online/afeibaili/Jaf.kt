package online.afeibaili

import online.afeibaili.command.JafCommand
import org.junit.jupiter.api.Test

class Jaf {
    @Test
    fun testReadLine() {
        var array: Array<String> =
            "add|B:\\Program Files\\Java\\jdk-1.8_421\\bin\\java.exe|-i|8".split("|").toTypedArray()

        array =
            "run 8".split(" ").toTypedArray()
        JafCommand.parse(array)
    }
}