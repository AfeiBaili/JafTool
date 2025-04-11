package online.afeibaili.file

import java.io.FileOutputStream
import java.io.InputStream

class FileWriter(val fileName: String, val path: String, val size: Long) {
    fun write(inputStream: InputStream) {
        var bytes = ByteArray(1024 * 1024 * 20)

        FileOutputStream(fileName).use { outputStream ->
            inputStream.use { inputStream ->
                var read = 0
                var bar: ProgressBar = ProgressBar(size)
                println()
                while (inputStream.read(bytes).also { read = it } != -1) {
                    outputStream.write(bytes, 0, read)
                    bar.move(read.toLong())
                }
            }
        }
    }
}