package online.afeibaili.data

import java.io.File

data class PathData(var path: String, var alias: String) {

    init {
        var file: File = File(path)
        path = file.canonicalPath
    }

    companion object {
        /**
         * 解析字符串形式的数据，格式为
         * "路径","别名" 包含双引号
         *
         * @param line 字符串形式的数据
         * @return 返回数据对象
         */
        fun parse(line: String): PathData {
            val parts = line.split(",")
            var path: String = parts[0].substring(1, parts[0].length - 1)
            var alias: String = parts[1].substring(1, parts[1].length - 1)
            return PathData(path, alias)
        }

        fun toLine(pathData: PathData): String {
            return "\"${pathData.path}\",\"${pathData.alias}\""
        }

        fun toPathData(array: ArrayList<String>): PathData {
            if (array.size == 1) return PathData(array[0], "")
            return PathData(array[0], array[1])
        }
    }
}