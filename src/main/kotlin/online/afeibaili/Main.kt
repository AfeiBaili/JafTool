package online.afeibaili


import online.afeibaili.command.JafCommand
import online.afeibaili.file.PathDataFileHandle
import java.io.File

val pathDataFile = StringBuilder(System.getProperty("user.dir")).append("/data/path.data").toString()
val fileHandler = PathDataFileHandle(File(pathDataFile)).load()

fun main(args: Array<String>) {
    JafCommand.parse(args)
}