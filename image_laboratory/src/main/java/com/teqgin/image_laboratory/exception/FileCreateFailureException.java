package com.teqgin.image_laboratory.exception;

public class FileCreateFailureException extends Exception {
    @Override
    public String getMessage() {
        return "创建文件夹失败";
    }
}
