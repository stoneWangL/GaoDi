package com.example.stonewang.gaodi.mode;

/**
 * Created by Lenovo on 2018/2/7.
 */

public class Comment {
    String name; //评论者
    String content; //评论内容

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
