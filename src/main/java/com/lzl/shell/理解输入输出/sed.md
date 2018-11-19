sed被称为流编辑器（stream editor），可以根据命令来处理数据流中的数据。这些命令要么从命令行中输入，要么存储在一个命令文本文件中。sed会执行一下操作：

（1）一次从输入中读取一行数据

（2）根据所提供的编辑器命令匹配数据

（3）按照命令修改流中的数据

（4）将新的数据输出到STDOUT

在流编辑器将所有命令与一行数据匹配完毕后，它会读取下一行数据并重复这个过程。在流编辑器处理完流中所有的数据行之后，它就会终止。

sed命令的格式为：

sed options script file

options中常用的选项如下所示：

 -e command   将command中的命令添加到已有的命令中，也就是说当sed中使用多个命令时要使用这个选项。
 -f file  将file中制定的命令添加到已有的命令中，注意每一个命令都要在独立的一行里。
 -i  直接修改源文件。
 -n 通常情况下，当所有命令执行完后输出会显示在STDOUT中，-n选项会禁止输出。通常和替换标记p一起使用，只输出被修改过的行。
script中的常用使用方式：

1. 替换文本中的指定字符，形式为：

sed 's/pattern/replacement/flags'

主要用法有（注意：如果不加上-i选项，修改的都是流中的内容，file本身的内容没有变化）：

sed 's/pattern/replacement/' file   将file的流中每行的第一个pattern替换成replacement
sed 's/pattern/replacement/g' file    将file的流中每行的每一个pattern替换成replacement
sed 's/pattern/replacement/2' file    将file的流中每行的第二个pattern替换成replacement
sed -n 's/pattern/replacement/p' file   将file的流中每行的第一个pattern替换成replacement，并打印出修改过的行
sed -n 's/pattern/replacement/2p' file   将file的流中每行的第二个pattern替换成replacement，并打印出修改过的行（标记符号可以多个一起使用，且顺序不影响结果）
sed 's/pattern/replacement/gw outFile' file   将file的流中每行的每一个pattern替换成replacement，并将输出保存到outFile中（w标记必须要放在最后，如果写作wg，标记g将无效，并生成名为g outFile的输出文件）
sed '2s/pattern/replacement/' file   将file的流中第二行的第一个pattern替换成replacement
sed '2,4s/pattern/replacement/' file   将file的流中第二行到第四行的第一个pattern替换成replacement
sed '2,$s/pattern/replacement/' file   将file的流中第二行到最后一行的第一个pattern替换成replacement
sed '/word/s/pattern/replacement/' file   将file的流中含有word的行中的第一个pattern替换成replacement，可以使用正则表达式
2. 删除文本

sed 'd' file   将file的流中所有行全部删除
sed '2d' file   将file的流中的第二行删除
sed '2,4d' file   将file的流中的第二行到第四行删除
sed '2,$d' file   将file的流中的第二行到最后一行删除
sed '/word/d' file   将file的流中含有word的行删除
3. 插入和附加文本

（1）插入（insert）命令（i）会在指定行前增加一个新行。

（2）附加（append）命令（a）会在指定行后增加一个新行。

格式为：

sed '[address]command\new line'

主要用法为（$和匹配模式/word/都可以使用）：

sed '3i\This a an inserted line.' file   将一个新行插入到file的流的第三行前
sed '3a\This a an inserted line.' file   将一个新行插入到file的流的第三行后
4. 修改行（$和匹配模式/word/都可以使用）：

sed '3c\This is a changed line.' file   将file的流的第三行修改为This is a changed line.
5. 转换命令

转换（transform）命令（y）是唯一可以处理单个字符的sed命令，格式如下：

sed '[address]y/inchars/outchars'

转换命令会对inchars和outchars做一对一映射，inchars的第一个字符会被转换为outchars的第一个字符，inchars的第二个字符会被转换为outchars的第二个字符。如果inchars和outchars的长度不同，sed编辑器会产生一条错误消息：

sed: 1: "y/abc/de/": transform strings are not the same length

举例如下：

$ echo "1 2 3 1 2 3 2 3 4 5 6" | sed 'y/123/456/'

4 5 6 4 5 6 5 6 4 5 6

可以看出，转换命令是一个全局命令，会在文本行中找到的所有指定字符进行转换，无法限定只转换在特定地方出现的字符。

7. 写入文件

sed 'w outFile' file   将file的内容写入到outFile中
sed '1,2w outFile' file   将file第一行到第二行的内容写入到outFile中
8. 从文件读取数据（$和匹配模式/word/都可以使用）：

sed '3r newFile' file   将newFile的内容添加到file的流的第三行之后
