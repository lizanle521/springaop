package com.lzl.lambada.chapter3.stream;

import com.lzl.lambada.chapter3.Book;
import com.lzl.lambada.chapter3.Topic;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.time.Year;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.*;

public class BookStreamTest {
    List<Book> library = new ArrayList<>();

    @Before
    public void before(){
        Book book1 = new Book("Fundamentals of Chinese Fingernail Image",
                Arrays.asList("Li", "Fu", "Li"),
                new int[]{256},
                Topic.MEDICINE,
                Year.of(2014),
                25.2);
        library.add(book1);
        Book book2 = new Book("Compilers:Principles,Techniques and Tools",
                Arrays.asList("Aho", "Lam", "Sethi","Ullman"),
                new int[]{1009},
                Topic.COMPUTING,
                Year.of(2006),
                23.6);

        Book book3 = new Book("Voss",
                Arrays.asList("Partric White"),
                new int[]{478},
                Topic.FICTION,
                Year.of(1957),
                19.8);


        Book book4 = new Book("Lord of Rings",
                Arrays.asList("Tolkien"),
                new int[]{531,416,624},
                Topic.FICTION,
                Year.of(1955),
                23.0);
        library.add(book2);
        library.add(book3);
        library.add(book4);
    }

    @Test
    public void topicFilter(){
        Stream<Book> bookStream = library.stream().filter(p -> p.getGetTopic() == Topic.COMPUTING);
        bookStream.forEach(System.out::println);
    }

    @Test
    public void getTitle(){
        Stream<String> stream = library.stream().map(Book::getTitle);
        stream.forEach(System.out::println);
    }

    @Test
    public void sortByTitle(){
        Stream<Book> sorted = library.stream().sorted(Comparator.comparing(Book::getTitle));
        sorted.forEach(System.out::println);

        Stream<String> sorted1 = library.stream().map(Book::getTitle).sorted();
        sorted1.forEach(System.out::println);

        Stream<Book> sorted2 = library.stream().sorted(Comparator.comparing(Book::getAuthors, Comparator.comparing(List::size)));
        sorted2.forEach(System.out::println);
    }

    @Test
    public void flatMap(){
        Stream<String> stringStream = library.stream().sorted(Comparator.comparing(Book::getTitle)).flatMap(p -> p.getAuthors().stream());
        stringStream.distinct().forEach(System.out::println);
    }

    @Test
    public void limit(){
        Stream<Book> limit = library.stream().sorted(Comparator.comparing(Book::getTitle)).limit(100);
        limit.forEach(System.out::println);
    }


    @Test
    public void skip(){
        Stream<Book> limit = library.stream().sorted(Comparator.comparing(Book::getTitle)).skip(100);
        limit.forEach(System.out::println);
    }

    @Test
    public void min(){
        Optional<Book> min = library.stream().min(Comparator.comparing(Book::getPubDate));
        System.out.println(min);
    }

    @Test
    public void collect(){
        Set<String> collect = library.stream().map(Book::getTitle).collect(Collectors.toSet());
        for (String s : collect) {
            System.out.println(s);
        }
    }

    @Test
    public void mapToInt(){
        int sum = library.stream().mapToInt(p -> p.getAuthors().size()).sum();
        System.out.println(sum);
    }

    @Test
    public void flatMapToInt(){
        IntStream intStream = library.stream().flatMapToInt(b -> IntStream.of(b.getPageCounts()));
        intStream.forEach(System.out::println);
    }

    @Test
    public void peek(){
        // 有副作用。只用于调试
        Stream<Book> peek = library.stream().filter(b -> b.getGetTopic() == Topic.COMPUTING).peek(b -> System.out.println("peek:"+b.getTitle()));
        List<Book> collect = peek.filter(b -> b.getAuthors().size() > 1).collect(Collectors.toList());
        for (Book book : collect) {
            System.out.println(book);
        }
    }

    @Test
    public void distinct(){
        Stream<String> distinct = library.stream().sorted(Comparator.comparing(Book::getTitle))
                .flatMap(b -> b.getAuthors().stream())
                .distinct();
        distinct.forEach(System.out::println);
    }

    @Test
    public void allmatch(){
        boolean b1 = library.stream().allMatch(b -> b.getHeight() > 10);
        System.out.println(b1);
    }

    @Test
    public void findAny(){
        Optional<Book> herma = library.stream().filter(b -> b.getAuthors().contains("Herma"))
                .findAny();
        System.out.println(herma.isPresent());
        System.out.println(herma.orElse(new Book()));
    }

    @Test
    public void findFirst(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("test.html"));
            Optional<String> test = bufferedReader.lines().filter(s -> s.contains("test")).findFirst();
            System.out.println(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void summarySatistics(){
        IntSummaryStatistics intSummaryStatistics = library.stream().mapToInt(b -> IntStream.of(b.getPageCounts()).sum()).summaryStatistics();
        System.out.println(intSummaryStatistics);
    }

    @Test
    public void natureorder(){
        Optional<String> min = library.stream().map(Book::getTitle).min(Comparator.naturalOrder());
        System.out.println(min);
    }

    @Test
    public void toMap(){
        Book book3 = new Book("Voss",
                Arrays.asList("Partric White"),
                new int[]{478},
                Topic.FICTION,
                Year.of(1958),
                19.8);
        library.add(book3);
        Map<String, Year> collect = library.stream().collect(Collectors.toMap(Book::getTitle, Book::getPubDate));
        System.out.println(collect.toString());
    }

    @Test
    public void toMap1(){
        Book book3 = new Book("Voss",
                Arrays.asList("Partric White"),
                new int[]{478},
                Topic.FICTION,
                Year.of(1958),
                19.8);
        library.add(book3);
        Map<String, Year> collect = library.stream().collect(Collectors.toMap(Book::getTitle, Book::getPubDate,
                (x, y) -> x.isAfter(y) ? x : y));
        System.out.println(collect);
    }

    /**
     * 不保证同步
     */
    @Test
    public void forEach(){
        AtomicLong pageCount = new AtomicLong(0);
        library.parallelStream().forEach(b->{pageCount.addAndGet(b.getPageCounts().length);});
        System.out.println(pageCount);
    }

    /**
     * 保留顺序并保证同步
     * 虽然可用但效率不高
     */
    @Test
    public void forEachOrdered(){

    }

    @Test
    public void sum(){
        int sum = library.stream().mapToInt(b -> b.getPageCounts().length).sum();
        System.out.println(sum);
    }

    /**
     * 收集与汇聚
     */
    @Test
    public void toList(){
        List<Book> collect = library.stream().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 根据主题分类
     */
    @Test
    public void groupingBy(){
        Map<Topic, List<Book>> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic));
        System.out.println(collect);
    }

    @Test
    public void toOrderMap(){
        /**
         * 按照title的字母顺序排序
         */
        TreeMap<String, Year> collect = library.stream().collect(Collectors.toMap(Book::getTitle, Book::getPubDate,
                BinaryOperator.maxBy(Comparator.naturalOrder()), TreeMap::new));
        System.out.println(collect);
    }

    /**
     * partitionby 是 groupby的一个便捷方法，他的map的key类型指定为boolean
     */
    @Test
    public void partitioinBy(){
        /**
         * 小说为true,非小说为false
         */
        Map<Boolean, List<Book>> collect = library.stream().collect(Collectors.partitioningBy(b -> b.getGetTopic() == Topic.FICTION));
        System.out.println(collect);
    }

    /**
     *
     */
    @Test
    public void groupingByWithmaxBy(){
        /**
         * 找到某个主题下作者最多的图书
         */
        Map<Topic, Optional<Book>> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic,
                Collectors.maxBy(Comparator.comparing(b -> b.getAuthors().size()))));
        System.out.println(collect);
    }

    @Test
    public void groupingByWithSummingint(){
        /**
         * 找到主题下边对应图书的总的卷数
         */
        Map<Topic, Integer> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic,
                Collectors.summingInt(b -> b.getPageCounts().length)));
        System.out.println(collect);
    }

    /**
     * 找到拥有最多图书的主题
     */
    @Test
    public void mostPupularTopic(){
        Optional<Topic> topic = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
        System.out.println(topic);
    }

    /**
     * 寻找最流行的主题
     */
    @Test
    public void mostPopularTopic(){
        Optional<Set<Topic>> topics = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toSet())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue);
        System.out.println(topics);
    }

    @Test
    public void concatanatedTitleByTopic(){
        /**
         * 将对应的主题 和 图书名字链接成的字符串对应起来
         */
        Map<Topic, String> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic,
                Collectors.mapping(Book::getTitle, Collectors.joining(";"))));
        System.out.println(collect);
    }

    @Test
    public void concatTitle(){
        /**
         * 所有图书的名字 用字符串链接起来
         */
        String collect = library.stream().map(Book::getTitle).collect(Collectors.joining(";"));
        System.out.println(collect);
    }

    @Test
    public void authorsForBooks(){
        /**
         * 创建一个字符列表，每个字符串都包含了书的作者的姓名
         */
        List<String> collect = library.stream().map(b -> b.getAuthors().stream()
                .collect(Collectors.joining(",", b.getTitle(), ",")))
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void toList1(){
        Map<String, List<Book>> collect = library.stream().collect(Collectors.groupingBy(Book::getTitle, Collectors.toList()));
        System.out.println(collect);
    }

    @Test
    public void counting(){
        Map<String, Long> collect = library.stream().collect(Collectors.groupingBy(Book::getTitle, Collectors.counting()));
        System.out.println(collect);
    }

    @Test
    public void mostAuthorByTopic(){
        Map<Topic, Optional<Book>> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.maxBy(Comparator.comparing(
                b -> b.getAuthors().size()
        ))));
        System.out.println(collect);
    }

    @Test
    public void volumeCountByTopic(){
        //计算每一个主题下的卷宗的数量 的两种方法
        Map<Topic, Integer> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.summingInt(b -> b.getPageCounts().length)));
        System.out.println(collect);

        //
        Map<Topic, Integer> collect1 = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.reducing(0, b -> b.getPageCounts().length, Integer::sum)));
        System.out.println(collect1);

    }


    @Test
    public void averageHeightByTopic(){
        Map<Topic, Double> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.averagingDouble(Book::getHeight)));
        System.out.println(collect);
    }

    @Test
    public void volumeStats(){
        Map<Topic, IntSummaryStatistics> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.summarizingInt(b -> b.getPageCounts().length)));
       // System.out.println(collect);

        System.out.println(collect.get(Topic.FICTION));
    }


    @Test
    public void collectAndThen(){
        List<String> collect = library.stream().map(Book::getTitle).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        System.out.println(collect);
    }

    /**
     * 每本书有多少页，每页有多厚
     * 计算每本书距离书架的初始位置的距离
     */
    @Test
    public void testDispRecord(){
        Supplier<Deque<DispRecord>> supplier = ArrayDeque::new;
        BiConsumer<Deque<DispRecord>,Book> accumlator = (dqLeft, b) -> {
            int disp = dqLeft.isEmpty() ? 0 : dqLeft.getLast().totalDisp();
            dqLeft.add(new DispRecord(b.getTitle(), disp, Arrays.stream(b.getPageCounts()).sum()));
        };
        BinaryOperator<Deque<DispRecord>> combiner = (left,right)->{
            if(left.isEmpty()) return right;
            int last = left.getLast().totalDisp();
            List<DispRecord> collect = right.stream().map(b -> new DispRecord(b.title, b.disp + last, b.lenght)).collect(Collectors.toList());
            left.addAll(collect);
            return left;
        };
        Function<Deque<DispRecord>,Map<String,Integer>> finisher = (ddr)->ddr.parallelStream().collect(Collectors.toConcurrentMap(dr->dr.title, dr->dr.disp));

        Map<String, Integer> collect = library.stream().collect(Collector.of(supplier, accumlator, combiner, finisher));
        System.out.println(collect);

    }

    /**
     * 引用流的汇聚
     *
     */
    @Test
    public void reduceRef(){
        //找到字母表顺序排第一位的一本书
        Comparator<Book> comparing = Comparator.comparing(Book::getTitle);
        Optional<Book> reduce = library.stream().reduce(BinaryOperator.minBy(comparing));
        System.out.println(reduce);

        //
        Stream<BigInteger> biStream = LongStream.of(1, 2, 3).mapToObj(BigInteger::valueOf);
        Optional<BigInteger> reduce1 = biStream.reduce(BigInteger::add);
        System.out.println(reduce1);

        // 一个流操作以后就会被关闭 ？
        biStream = LongStream.of(1, 2, 3).mapToObj(BigInteger::valueOf);
        BigInteger reduce2 = biStream.reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println(reduce2);
    }


    @Test
    public void reduceAccumlator(){
        /**
         * reduce的 积聚 和 组合
         */
        Integer reduce = library.stream().reduce(0, (s, book) -> s + book.getPageCounts().length, Integer::sum);
        System.out.println(reduce);

        /**
         * reduce 的map 与 reduce
         */
        int sum = library.stream().mapToInt(b -> b.getPageCounts().length).sum();
        System.out.println(sum);
    }

    public Deque<DispRecord> wrap(DispRecord dispRecord){
        Deque<DispRecord> ddr = new ArrayDeque<>();
        ddr.add(dispRecord);
        return ddr;
    }

    @Test
    public void testMapReduce(){
        BinaryOperator<Deque<DispRecord>> combiner = (left,right)->{
            if(left.isEmpty()) return right;
            int last = left.getLast().totalDisp();
            List<DispRecord> collect = right.stream().map(b -> new DispRecord(b.title, b.disp + last, b.lenght)).collect(Collectors.toList());
            left.addAll(collect);
            return left;
        };
        ConcurrentMap<String, Integer> collect = library.stream().map(DispRecord::new)
                .map(this::wrap)
                .reduce(combiner).orElseGet(ArrayDeque::new)
                .stream()
                .collect(Collectors.toConcurrentMap(dr -> dr.title, dr -> dr.disp));
        System.out.println(collect);
    }

    @Test
    public void testReducing(){
        // 找出每个主题中高度最高的书
        Comparator<Book> comparing = Comparator.comparing(Book::getHeight);
        Map<Topic, Optional<Book>> collect = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.reducing(BinaryOperator.maxBy(comparing))));
        System.out.println(collect);

        // 每个主题下书本的数量
        Map<Topic, Long> collect1 = library.stream().collect(Collectors.groupingBy(Book::getGetTopic, Collectors.reducing(0L, b -> 1L, Long::sum)));
        System.out.println(collect1);
    }

    @Test
    public void testRangeClosed(){
        Stream<String> stringStream = library.stream().map(b -> {
            int[] pageCounts = b.getPageCounts();
            return IntStream.rangeClosed(1, pageCounts.length)
                    .mapToObj(i -> i + ":" + pageCounts[i - 1]).collect(Collectors.joining(",", b.getTitle() + ";", ","));

        });
        stringStream.forEach(System.out::println);
    }



}
