package com.lzl.design_pattern.A_Iterator.classes;

import com.lzl.design_pattern.A_Iterator.interfaces.Iterator;

public class BookShelfIterator implements Iterator {

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        index = 0;
    }

    private BookShelf bookShelf;

    private int index ;

    @Override
    public boolean hasNext() {
        if(index < bookShelf.getLength()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Object next() {
        Book book = bookShelf.getBookAt(index);
        index ++;
        return book;
    }
}
