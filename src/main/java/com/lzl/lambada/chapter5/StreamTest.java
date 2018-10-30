package com.lzl.lambada.chapter5;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.IntUnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 起始流
 */
public class StreamTest {
    @Test
    public void testIterate(){
        // 在 1 与 -1 之间交替变换的流
        IntStream iterate = IntStream.iterate(1, i -> -i);

    }

    @Test
    public void testGenerate(){
        // 生成int 值的流，每个值都是1
        IntStream generate = IntStream.generate(() -> 1);
    }

    @Test
    public void testRange(){
        // 包含5
        IntStream.rangeClosed(1, 5).forEach(System.out::print);
        System.out.println();
        // 不包含 5
         IntStream.range(1, 5).forEach(System.out::print);
    }

    @Test
    public void printFileInfo(){
        /**
         * 打印当前目录下的所有文件
         */
        File file = new File(".");
        Path path = file.toPath();
        try (Stream<Path> walk = Files.walk(path)) {
            walk.map(Path::toFile)
                    .filter(File::isFile)
                    .map(f->f.getAbsolutePath()+"  "+f.length())
                    .forEachOrdered(System.out::println);
        }catch (IOException e){

        }

        // 拋出受檢查的異常
        //Files.list(path)
        //        .flatMap(Files::lines)
        //        .forEachOrdered(System.out::println);

         file = new File("dir");
         path = file.toPath();
        try( Stream<Path> list = Files.list(path);) {
            list.peek(p->{
                try {
                    Files.lines(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).forEach(System.out::println);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        file = new File(".");
        path = file.toPath();
        try(Stream<Path> list = Files.list(path);) {
            list.flatMap(p->{
                Stream<String> lines;
                try {
                    lines = Files.lines(p);
                } catch (IOException e) {
                    e.printStackTrace();
                    lines = Stream.of("exception"+e.toString());
                }
                return lines;
            }).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void splitor(){

    }

    @Test
    public void grepH(){
        // 实现grep -Rh的功能
        //  递归打印当前目录下文件名匹配 java这个正则的文件中的任意含有 test字符的行
        File file = new File(".");
        Path path = file.toPath();
        Pattern compile = Pattern.compile("test");
        // 匹配文件名则用 glob, 匹配正则表达式则用 regex
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:"+"java");
        try(Stream<Path> walk = Files.walk(path)){
            walk.filter(Files::isRegularFile)
                    .filter(pathMatcher::matches)
                    .flatMap(path1->{
                        try {
                            return Files.readAllLines(path1).stream();
                        } catch (IOException e) {
                            return Stream.of("");
                        }
                    })
                    .filter(line -> !line.isEmpty())
                    .filter(line->compile.matcher(line).find())
                    .forEach(System.out::println);
        }catch (Exception e){

        }
    }

    @Test
    public void testGrepRH(){
        // 递归打印当前文件夹下 匹配java文件名的包含 test字符的行，并以文件名为前缀
        Path path = new File(".").toPath();
        Pattern pattern = Pattern.compile("test");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:test.html");

        try(Stream<Path> walk = Files.walk(path)){
            walk.filter(Files::isRegularFile)
                    .filter(pathMatcher::matches)
                    .flatMap(p->{
                        try {
                            return Files.readAllLines(p).stream()
                                    .filter(l -> pattern.matcher(l).find())
                                    .map(l -> p + ":" + l);
                        }catch (Exception e){
                            return Stream.of("");
                        }
                    })
                    .filter(l->!l.isEmpty())
                    .forEach(System.out::println);
        }catch (Exception e){

        }

    }
}
