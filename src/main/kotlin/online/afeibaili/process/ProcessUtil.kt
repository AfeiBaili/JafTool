package online.afeibaili.process

import java.nio.charset.Charset

object ProcessUtil {
    fun simpleProcess(vararg command: String, redirect: Boolean = true): Process {
        return ProcessBuilder(*command).redirectErrorStream(redirect).start()
    }

    fun printSimpleProcess(vararg command: String, redirect: Boolean = true): Int {
        var simpleProcess: Process = simpleProcess(*command, redirect = redirect)
        simpleProcess.inputReader().forEachLine { println(it) }
        return simpleProcess.waitFor()
    }

    fun runJavaProcess(path: String, command: Array<String>, redirect: Boolean = true) {
        var process: Process = ProcessBuilder(path, *command).redirectErrorStream(redirect).start()
        println("default\t" + "用法".toByteArray(Charset.defaultCharset()).joinToString(" "))
        println("utf-8\t" + "用法".toByteArray(Charset.forName("UTF-8")).joinToString(" "))
        println("utf-16\t" + "用法".toByteArray(Charset.forName("UTF-16")).joinToString(" "))
        println("gbk\t" + "用法".toByteArray(Charset.forName("gbk")).joinToString(" "))
        process.inputStream.bufferedReader(Charset.forName("GBK")).forEachLine { println(it) }
    }
}