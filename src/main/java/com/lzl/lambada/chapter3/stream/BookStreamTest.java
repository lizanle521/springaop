package com.lzl.lambada.chapter3.stream;

import com.lzl.lambada.chapter3.Book;
import com.lzl.lambada.chapter3.Topic;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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
        library.stream().sorted(Comparator.comparing(Book::getTitle));
    }
}
