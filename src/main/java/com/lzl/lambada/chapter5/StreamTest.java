package com.lzl.lambada.chapter5;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.IntUnaryOperator;
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
}
