package online.afeibaili.download

import online.afeibaili.Util
import online.afeibaili.file.FileWriter
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.InputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object JdkDownload {
    val httpClient: HttpClient = HttpClient.newHttpClient()
    fun listJdk(version: String) {
        var uri: URI = URI("https://www.oracle.com/java/technologies/javase/jdk${version}-archive-downloads.html")
        var request: HttpRequest = HttpRequest.newBuilder(uri).GET().build()
        var response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        var tables: Elements = Jsoup.parse(response.body()).getElementsByTag("table")

        var jdks: ArrayList<Jdk> = ArrayList<Jdk>()
        tables.forEach { table ->
            table.getElementsByTag("tbody").forEach { tbody ->
                tbody.getElementsByTag("tr").forEach { tr ->
                    var tds: Elements = tr.getElementsByTag("td")
                    var a: Elements = tds[2].getElementsByTag("a")
                    var jdk: Jdk = Jdk(tds[0].text(), tds[1].text(), a[0].attr("href"), a[1].attr("href"))

                    jdks.add(jdk)
                }
            }
        }

        println("==JDK${version}所有版本(Oracle) Http响应代码${response.statusCode()}==================================\n")
        jdks.forEach { jdk -> println("系统：${jdk.name}\t大小：${jdk.size}\n链接：${jdk.link}") }
    }

    fun downloadFileByDefaultSystem(version: String, format: String) {
        var systemName: String = System.getProperty("os.name")
        var system: Any = when {
            systemName.contains("windows", ignoreCase = true) -> "windows"
            systemName.contains("mac", ignoreCase = true) -> "macos"
            systemName.contains("linux", ignoreCase = true) -> "linux"
            else -> Util.exit("暂不支持的系统")
        }
        downloadFile(version, format, system as String)
    }

    fun downloadFile(
        version: String,
        format: String,
        system: String,
        schema: String = "x64",
        isLatest: Boolean = false,
    ) {
        var uri: URI =
            if (isLatest) URI("https://download.oracle.com/java/$version/latest/jdk-${version}_$system-${schema}_bin.$format")
            else URI("https://download.oracle.com/java/$version/archive/jdk-${version}_$system-${schema}_bin.$format")

        var request: HttpRequest = HttpRequest.newBuilder(uri).GET().build()
        var response: HttpResponse<InputStream> = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())

        var size: Long = response.headers().map()["content-length"].toString().let { it ->
            it.substring(1, it.length - 1).toLong()
        }

        if (response.statusCode() != 200) Util.exit("找不到指定版本请检查参数：${uri}")
        FileWriter(uri.toString().substringAfterLast("/"), System.getProperty("user.dir"), size).write(response.body())
    }
}