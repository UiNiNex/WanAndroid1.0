package wanandroid.c03.jy.com.wanandroid;

/*
 * created by taofu on 2018/11/27
 **/
public class AppConstant {

    public static final String BASE_URL = "http://www.wanandroid.com";
    //https://api.apiopen.top/videoCategoryDetails?id=14

    public static final String SAVE_USER_LOGIN_KEY = "user/login";
    public static final String SAVE_USER_REGISTER_KEY = "user/register";
    public static final String SET_COOKIE_KEY = "set-cookie";

    public static final String COLLECTIONS_WEBSITE = "lg/collect";
    public static final String UNCOLLECTIONS_WEBSITE = "lg/uncollect";
    public static final String ARTICLE_WEBSITE = "article";
    public static final String TODO_WEBSITE = "lg/todo";

    public static final String COOKIE_NAME = "Cookie";

    public static final String VIDEO_URL = "https://api.apiopen.top";

    public static final String EDIT_ERROR_COLOR = "#00ffff";
    public static final String EDIT_ERROR_PASSWORD_AND_REPASSWORD_MASSAGE = "两次密码不相同";
    public static final String EDIT_ERROR_STRING_SIZE_MASSAGE = "账号密码必须大于6位小于12位";
    public static final String EDIT_ERROR_USERNAME_NULL_MASSAGE = "账号不能为空";
    public static final String EDIT_ERROR_PASSWORD_NULL_MASSAGE = "密码不能为空";
    public static final String LOGGING_STATUS = "loggingstatus";
    public static final String KEY_UM_HEAD_PROFILE_URL = "KEY_UM_HEAD_PROFILE_URL";
    public static final String KEY_UM_HEAD_PROFILE_STAUTUS = "KEY_UM_HEAD_PROFILE";
    public static final String VIDEO_NO_ITEM = "\"没有更多数据了\"";

    public interface ParamsUser{

        String KEY_USER_NAME = "username";
        String KEY_USER_PASSWORD = "password";
        String KEY_USER_REPASSWORD = "repassword";

    }


}
