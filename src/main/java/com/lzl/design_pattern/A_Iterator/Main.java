package com.lzl.design_pattern.A_Iterator;

import com.lzl.design_pattern.A_Iterator.classes.Book;
import com.lzl.design_pattern.A_Iterator.classes.BookShelf;
import com.lzl.design_pattern.A_Iterator.interfaces.Iterator;

public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(5);
        bookShelf.addBook(new Book("aaa"));
        bookShelf.addBook(new Book("aaa"));
        bookShelf.addBook(new Book("aaa"));
        bookShelf.addBook(new Book("aaa"));
        bookShelf.addBook(new Book("aaa"));
        Iterator iterator = bookShelf.iterator();
        while (iterator.hasNext()){
            Book next = (Book)iterator.next();
            System.out.println(next.getName());
        }
    }
}
