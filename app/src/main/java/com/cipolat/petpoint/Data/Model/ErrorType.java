package com.cipolat.petpoint.Data.Model;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class ErrorType {
    private Throwable t;

    public ErrorType(Throwable t) {
        this.t = t;
    }

    public boolean isNetworkError() {
        return t instanceof IOException || t instanceof SocketTimeoutException;
    }

    public boolean isServerError() {
        return t instanceof HttpException || t instanceof com.google.gson.JsonSyntaxException;
    }
}

