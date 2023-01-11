package com.articles.response;

public class Response<T, V> {

    private final T exception;
    private final V successBody;


    public Response(T exception, V successBody) {
        this.exception = exception;
        this.successBody = successBody;
    }

    public T getException() {
        return exception;
    }

    public V getSuccessObject() {
        return successBody;
    }

}