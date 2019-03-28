package wanandroid.c03.jy.com.wanandroid.data.entity;

/*
 * created by taofu on 2018/11/27
 **/
public class HttpResult<T> {

    public T data;
    public int errorCode;
    public String errorMsg;
    public HttpResult() {
        super();
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    public HttpResult(T data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


}
