package online.afeibaili.file

class ProgressBar(val totalSize: Long) {
    var size = 0L
    var progress = 0.0

    fun move(currentSize: Long) {
        size += currentSize
        progress = (size.toDouble() / totalSize.toDouble()) * 100
        var progressString: String = "%.2f".format(progress)
        var progressDone: Int = progress.toInt()
        print("\r[${"=".repeat(progressDone)}${" ".repeat(100 - progressDone)}]%$progressString")
    }
}