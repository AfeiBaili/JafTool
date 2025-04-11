package online.afeibaili

import online.afeibaili.file.PathDataFileHandle
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

val pathDataFile = StringBuilder(System.getProperty("user.dir")).append("/data/path.data").toString()
val fileHandler = PathDataFileHandle(File(pathDataFile)).load()

fun main(args: Array<String>) {
//    JafCommand.parse(args)
    var connection: URLConnection =
        URL("https://download.oracle.com/java/24/latest/jdk-24_windows-x64_bin.zip").openConnection()

    var conn: HttpURLConnection = connection as HttpURLConnection
    conn.requestMethod = "HEAD"
    if (conn.responseCode == HttpURLConnection.HTTP_OK) {
        var lng: Long = conn.contentLengthLong / 1024 / 1024
        println("大小为：${lng}m")
    }
}