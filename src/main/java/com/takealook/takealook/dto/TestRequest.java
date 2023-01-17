package com.takealook.takealook.dto;

public class TestRequest {
    private String title;
    private String content;

    public TestRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
