package online.afeibaili.command

import online.afeibaili.Util
import online.afeibaili.data.PathData
import online.afeibaili.download.JdkDownload
import online.afeibaili.fileHandler
import online.afeibaili.param.Parameter
import online.afeibaili.process.ProcessUtil
import online.afeibaili.text.Message

object JafCommand {
    val commandSet: CommandSet = CommandSet()

    val help: String = """
                        ==========================
                        Jaf Java版本管理&下载工具
                        ==========================
                        
                        运行指定的Java版本：
                        run <指定的Java别名>
                            例如：run 8
                            
                        查看指定的Java版本：
                        <download | -d> <17至当前的Java版本> <--list | -l>
                            例如：download 17 --list
                            
                        下载JDK：
                        <download | -d> <17至当前的Java版本> [<--system | -s> <操作系统>] <--format | -f> <文件格式>
                            例如：download 17 --system windows --format zip
                            例如：download 17 --format zip         #默认本机操作系统
                            
                        运行指定的Java程序和版本：
                        <execute | -e> <指定的Java别名> <--command | -c> <bin下的程序>
                            例如：execute 8 command jsp
                            
                        添加一个路径和别名：
                        <add | -a> <Java主目录> <--alias | -i> <Java的别名>
                            例如：add /jdk8 -i 8
                            
                        移除一个路径：
                        <remove | -r> <Java主目录 | 别名>
                            例如：change /java
                            
                        更新一个路径的别名：
                        <change | -c> <Java主目录> <--alias | -i> <Java的别名>
                            例如：change /java -i 9
                            
                        显示所有的路径和别名：
                        <list | -l>
                            例如：list
                            
                        帮助：
                        <help | -h>
                    """.trimIndent()


    init {
        commandSet.add(
            CommandBuilder().param(Parameter("run", "-r", { s, c ->
                var pathDataSet: HashSet<PathData> = fileHandler.getSet()
                pathDataSet.forEach {
                    if (s == it.alias) {
                        c.put("path", it.path)
                        return@Parameter true
                    }
                }
                println("无可用路径别名")
                false
            })).build({ command, context ->
                ProcessUtil.runJavaProcess(context["path"] as String, command.otherParam!!.toTypedArray())
            }, ArrayList<String>())
        )

        commandSet.add(
            CommandBuilder().param(Parameter("download", "-d", { s, m ->
                m.put("version", s)
                true
            })).param(Parameter("--list", "-l", false)).build { command, context ->
                JdkDownload.listJdk(context["version"] as String)
            }
        )

        commandSet.add(
            CommandBuilder().param(Parameter("download", "-d", { s, m ->
                m.put("version", s)
                true
            })).param(
                Parameter("--system", "-s", { s, m ->
                    m.put("system", s)
                    true
                })
            ).param(Parameter("--format", "-f", { s, m ->
                m.put("format", s)
                Util.isExistFormat(s)
            })).build { command, context ->
                JdkDownload.downloadFile(
                    context["version"] as String,
                    context["format"] as String,
                    context["system"] as String
                )
            }
        )

        commandSet.add(
            CommandBuilder().param(Parameter("download", "-d", { s, m ->
                m.put("version", s)
                true
            })).param(Parameter("--format", "-f", { s, m ->
                m.put("format", s)
                Util.isExistFormat(s)
            })).build { command, context ->
                JdkDownload.downloadFileByDefaultSystem(context["version"] as String, context["format"] as String)
            }
        )

        commandSet.add(
            CommandBuilder().param(Parameter("download", "-d", { s, m ->
                true
            })).param(Parameter("--format", "-f", { s, m ->
                true
            })).build { command, context ->

            }
        )

        commandSet.add(
            CommandBuilder().param(Parameter("execute", "-e", { s, c ->
                var pathDataSet: HashSet<PathData> = fileHandler.getSet()
                pathDataSet.forEach {
                    if (s == it.alias) {
                        c.put("path", it.path)
                        return@Parameter true
                    }
                }
                println("无可用路径别名")
                false
            })).param(Parameter("--command", "-c", { s, c ->
                c.put("command", s)
                true
            })).build({ command, context ->
                ProcessUtil.executeJavaProcess(
                    context["path"] as String,
                    context["command"] as String,
                    command.otherParam!!.toTypedArray()
                )
            }, ArrayList<String>())
        )

        commandSet.add(
            CommandBuilder().param(Parameter("add", "-a", { s, c ->
                c.put("path", s)
                fileHandler.isFileExist(s) && fileHandler.isAliasExist(s)
            })).param(Parameter("--alias", "-i", { s, c ->
                c.put("alias", s)
                true
            })).build { command, context ->
                fileHandler.addData(
                    PathData(
                        context["path"] as String,
                        context["alias"] as String
                    )
                )
                println("添加成功")
            })

        commandSet.add(
            CommandBuilder().param(Parameter("remove", "-r", { s, c ->
                c.put("pathOrAlias", s)
                true
            })).build { command, context ->
                var isSuccess: Boolean =
                    fileHandler.removeData(PathData(context["pathOrAlias"] as String, context["pathOrAlias"] as String))
                println(if (isSuccess) "删除成功" else "删除失败")
            })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("change", "-c", { s, c ->
                    c.put("path", s)
                    fileHandler.isFileExist(s) && fileHandler.isAliasExist(s)
                }))
                .param(Parameter("--alias", "-i", { s, c ->
                    c.put("alias", s)
                    true
                }))
                .build { command, context ->
                    var changeData: Boolean = fileHandler.changeData(
                        PathData(
                            context["path"] as String,
                            context["alias"] as String
                        )
                    )
                    println(if (changeData) "修改成功" else "没有可匹配的数据")
                })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("list", "-l", false))
                .build { command, context ->
                    fileHandler.getSet().forEach { println("路径  ${it.path}    |  别名  ${it.alias}") }
                })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("--help", "-h", false))
                .build { command, context ->
                    println(help)
                })
    }

    fun parse(args: Array<String>) {
        var parses: Message = commandSet.parses(args)
        if (parses.matchValue == 0) println(help)
        else println(parses)
    }
}