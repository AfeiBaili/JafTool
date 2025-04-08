package online.afeibaili.command

import online.afeibaili.data.PathData
import online.afeibaili.fileHandler
import online.afeibaili.param.Parameter
import online.afeibaili.text.Message

object JafCommand {
    val commandSet: CommandSet = CommandSet()

    init {
        commandSet.add(
            CommandBuilder().param(Parameter("add", "a", { s, c ->
                c.put("path", s)
                fileHandler.isFileExist(s)
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
            CommandBuilder().param(Parameter("remove", "r", { s, c ->
                c.put("pathOrAlias", s)
                true
            })).build { command, context ->
                var isSuccess: Boolean =
                    fileHandler.removeData(PathData(context["pathOrAlias"] as String, context["pathOrAlias"] as String))
                println(if (isSuccess) "删除成功" else "删除失败")
            })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("change", "c", { s, c ->
                    c.put("path", s)
                    fileHandler.isFileExist(s)
                }))
                .param(Parameter("--alias", "-i", { s, c ->
                    c.put("alias", s)
                    true
                }))
                .build { command, context ->
                    fileHandler.changeData(
                        PathData(
                            context["path"] as String,
                            context["alias"] as String
                        )
                    )
                    println("修改成功")
                })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("list", "l", false))
                .build { command, context ->
                    fileHandler.getSet().forEach { println("路径  ${it.path}    |  别名  ${it.alias}") }
                })

        commandSet.add(
            CommandBuilder()
                .param(Parameter("--help", "-h", false))
                .build { command, context ->
                    println(
                        """
                        ==========================
                        Jaf
                        ==========================
                            
                        [add | a] [路径] [--alias | -i] [Java的别名]       添加一个路径和别名 
                            例如：add /java-jdk8/bin -i 8
                            
                        [remove | r] [路径 | 别名]                         移除一个路径
                            例如：change /java/bin
                            
                        [change | c] [路径] [--alias | -i] [Java的别名]    更新一个路径的别名 
                            例如：change /java/bin -i 9
                            
                        [list | l]                                       显示所有的路径和别名
                            例如：list
                            
                        [--help | -h]                                    帮助 
                    """.trimIndent()
                    )
                })
    }

    fun parse(args: Array<String>) {
        var parses: Message = commandSet.parses(args)
        println(parses)
    }
}