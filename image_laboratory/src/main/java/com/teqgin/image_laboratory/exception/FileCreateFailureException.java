package com.teqgin.image_laboratory.exception;

/**
 * 创建文件失败异常
 */
public class FileCreateFailureException extends Exception {
    @Override
    public String getMessage() {
        return "创建文件夹失败";
    }
}
