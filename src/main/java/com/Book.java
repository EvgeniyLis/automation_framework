package com;

public class Book {
    private String isbn;
    private String price_net;
    private String url;

    public Book(String isbn, String price_net, String url) {
        this.isbn = isbn;
        this.price_net = price_net;
        this.url = url;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice_net() {
        return price_net;
    }

    public void setPrice_net(String price_net) {
        this.price_net = price_net;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
