package wanandroid.c03.jy.com.wanandroid.exception;

import android.content.Context;

import wanandroid.c03.jy.com.wanandroid.R;


/*
 * created by taofu on 2018/11/30
 **/
public class ExceptionManager {


    //构建一个 serverException
    public static Throwable buildServerErrorMessage(int code,String msg){
        return new ServerException(code, msg);
    }


    //从 throwable 里面提出出 错入信息
    public static String getMsgFromThrowable(Context context,Throwable throwable){
        if(throwable instanceof  ServerException){
            return throwable.getMessage();
        }else{
            return context.getString(R.string.text_net_error_retry);
        }
    }
}
