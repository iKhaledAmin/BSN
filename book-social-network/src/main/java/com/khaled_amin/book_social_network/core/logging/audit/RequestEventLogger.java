package com.khaled_amin.book_social_network.core.logging.audit;

public interface RequestEventLogger {
    void logStart(String method, String path);
    void logComplete(String method, String path,int statusCode, long duration);
}
