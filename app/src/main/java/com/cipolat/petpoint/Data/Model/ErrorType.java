package com.cipolat.petpoint.Data.Model;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by sebastian on 01/02/18.
 */

public class ErrorType {
    private Throwable t;

    public ErrorType(Throwable t) {
        this.t = t;
    }

    public boolean isNetworkError() {
        if (t instanceof IOException || t instanceof SocketTimeoutException)
            return true;
        else
            return false;
    }

    public boolean isServerError() {
        if (t instanceof HttpException || t instanceof com.google.gson.JsonSyntaxException)
            return true;
        else
            return false;
    }
}

