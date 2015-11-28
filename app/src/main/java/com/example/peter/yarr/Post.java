package com.example.peter.yarr;


/**
 * Created by peter on 27/11/15.
 */
public class Post {
    String title;
    int score;
    String author;
    String link;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
