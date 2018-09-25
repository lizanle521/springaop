package com.lzl.lambada.chapter3;

import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private int[] pageCounts;
    private Topic getTopic;
    private Year pubDate;
    private double height;

    public Book(String title, List<String> authors, int[] pageCounts, Topic getTopic, Year pubDate, double height) {
        this.title = title;
        this.authors = authors;
        this.pageCounts = pageCounts;
        this.getTopic = getTopic;
        this.pubDate = pubDate;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int[] getPageCounts() {
        return pageCounts;
    }

    public void setPageCounts(int[] pageCounts) {
        this.pageCounts = pageCounts;
    }

    public Topic getGetTopic() {
        return getTopic;
    }

    public void setGetTopic(Topic getTopic) {
        this.getTopic = getTopic;
    }

    public Year getPubDate() {
        return pubDate;
    }

    public void setPubDate(Year pubDate) {
        this.pubDate = pubDate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", pageCounts=" + Arrays.toString(pageCounts) +
                ", getTopic=" + getTopic +
                ", pubDate=" + pubDate +
                ", height=" + height +
                '}';
    }
}
