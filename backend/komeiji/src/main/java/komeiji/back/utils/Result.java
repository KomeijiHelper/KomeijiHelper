package komeiji.back.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Schema(name = "CommonResult", description = "通用返回对象")
public class Result<T> {
    @Schema(description = "返回码",example = "200")
    private String code;

    @Schema(description = "返回信息",example = "成功")
    private String msg;

    @Schema(description = "返回数据",example = "xxxxx")
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data,String msg) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(int code, String msg, HttpServletResponse response) throws IOException {
        Result<T> result = new Result<>();
        response.setStatus(code);
        result.setMsg(msg);
        return result;
    }
}
