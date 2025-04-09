package online.afeibaili.file

import online.afeibaili.Util.getDateTime
import online.afeibaili.data.PathData
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class PathDataFileHandle(private val file: File) {
    private val pathDataSet: HashSet<PathData> = HashSet()

    fun load(): PathDataFileHandle {
        if (!file.exists()) initPathDataFile()
        map()
        return this
    }

    fun getSet(): HashSet<PathData> = pathDataSet

    fun isFileExist(path: String): Boolean = File(path).exists()
    fun isJavaApplication(path: String): Boolean = when (file.extension) {
        "java.exe" -> true
        "javaw.exe" -> true
        else -> false
    }

    fun isAliasExist(path: String): Boolean {
        pathDataSet.forEach { it ->
            println("${path}已存在 存在于$it")
            if (it.alias == path) return false
        }
        return true
    }

    fun addData(pathData: PathData): Boolean {
        var add: Boolean = pathDataSet.add(pathData)
        write()
        return add
    }

    fun changeData(pathData: PathData): Boolean {
        pathDataSet.forEach { it ->
            if (it.path == pathData.path) {
                pathDataSet.remove(it)
                pathDataSet.add(pathData)
                write()
                return true
            }
        }
        return false
    }

    fun removeData(pathData: PathData): Boolean {
        pathDataSet.forEach {
            if (it.path == pathData.path || it.alias == pathData.alias) {
                pathDataSet.remove(it)
                write()
                return true
            }
        }
        return false
    }

    private fun write(vararg message: String) {
        FileWriter(file).use { fw ->
            message.forEach {
                if (it != "") {
                    fw.write("# $it\n")
                    fw.write("\n")
                    fw.flush()
                }
            }
            pathDataSet.forEach { fw.write("${PathData.toLine(it)}\n") }
        }
    }

    private fun write() {
        write("修改时间：${getDateTime()}")
    }

    /**
     * 将数据映射成对象
     *
     * @return 返回对象集
     */
    private fun map(): HashSet<PathData> {
        BufferedReader(FileReader(file)).use { br ->
            var line = ""
            while (br.readLine().also { line = it } != null) {
                if (line.startsWith("#")) continue
                if (line.trim() == "") continue
                pathDataSet.add(PathData.parse(line))
            }
        }
        return pathDataSet;
    }

    private fun initPathDataFile() {
        file.parentFile.mkdirs()
        file.createNewFile()
    }
}