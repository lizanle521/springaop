package com.lzl.design_pattern.A_Iterator.classes;

import com.lzl.design_pattern.A_Iterator.interfaces.Aggregate;
import com.lzl.design_pattern.A_Iterator.interfaces.Iterator;

public class BookShelf implements Aggregate {
    private Book[] books;
    /**
     * 保存最后添加的book的下一个位置
     */
    private int last;

    public BookShelf(int size) {
        if(size <= 0){
            throw new IllegalArgumentException("size cannot be zero or negative");
        }
        this.books = new Book[size];
    }


    public void addBook(Book book){
        books[last] = book;
        last ++;
    }

    public Book getBookAt(int index){
        return books[index];
    }

    public int getLength(){
        return last;
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
