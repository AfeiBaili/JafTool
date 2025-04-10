package online.afeibaili.process

import online.afeibaili.Util
import online.afeibaili.fileHandler
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

    fun runJavaProcess(path: String, param: Array<String>, redirect: Boolean = true) {
        executeJavaProcess(path, "java", param, redirect)
    }

    fun executeJavaProcess(path: String, command: String, param: Array<String>, redirect: Boolean = true) {
        var javaBinPath: String? = fileHandler.getJavaBinPath(path)
        if (javaBinPath == null) {
            Util.exit("提供的路径没有bin目录")
        }
        var fullPath: String = "${javaBinPath}\\$command"

        println(fullPath)
        var process: Process = ProcessBuilder(fullPath, *param).redirectErrorStream(redirect).start()
        process.inputStream.bufferedReader(Charset.forName("GBK")).forEachLine { println(it) }
    }
}