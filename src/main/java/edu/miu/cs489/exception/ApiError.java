package edu.miu.cs489.exception;

public record ApiError (
    String message,
    String path,
    Integer statusCode,
    long timeStamp)
{}
