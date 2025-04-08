package online.afeibaili.process

object ProcessUtil {
    fun simpleProcess(vararg command: String, redirect: Boolean = true): Process {
        return ProcessBuilder(*command).redirectErrorStream(redirect).start()
    }

    fun printSimpleProcess(vararg command: String, redirect: Boolean = true): Int {
        var simpleProcess: Process = simpleProcess(*command, redirect = redirect)
        simpleProcess.inputReader().forEachLine { println(it) }
        return simpleProcess.waitFor()
    }
}