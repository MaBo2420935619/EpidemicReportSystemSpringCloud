package com.mabo.global;

public class Result<T> {

    private int code;       // 状态码
    private String msg;     // 返回的信息
    private T data;         // 返回的数据
    public enum ResultType {
        SUCCESS,Error
    }
    /**
     * 成功时候的调用（有数据）
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data, ResultType.SUCCESS);
    }

    /**
     * 成功时候的调用（无数据）
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(){
        return new Result<T>();
    }

    /**
     * 异常时候的调用（有msg参数）
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(T msg){
        return new Result<T>(msg, ResultType.Error);
    }

    /**
     * 异常时候的调用（无msg参数）
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(){
        return new Result<T>("error");
    }

    private Result(T data,ResultType type) {
        if (type== ResultType.SUCCESS){
            this.code = 200;
            this.msg = "success";
        }else if (type== ResultType.Error){
            this.code = 400;
            this.msg = "error";
        }
        this.data = data;
    }

    private Result() {
        this.code = 200;
        this.msg = "success";
    }

    public Result(String msg) {
        this.code = 400;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
