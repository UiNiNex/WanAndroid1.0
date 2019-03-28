package wanandroid.c03.jy.com.wanandroid.data.remota.rteofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wanandroid.c03.jy.com.wanandroid.data.entity.ArticleData;
import wanandroid.c03.jy.com.wanandroid.data.entity.Data;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeBanner;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:10:29
 * author:lzy
 */
public interface WARetrofitService {

    @POST("user/register")
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> params);

    @POST("user/login")
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@FieldMap Map<String, String> params);

    @GET("article/list/{page}/json")
    Observable<HttpResult<ArticleData>> ramotHomeUser(@Path("page") int page);

    @GET("banner/json")
    Observable<HttpResult<List<HomeBanner>>> ramotBannerHomeUser();

    @GET("navi/json")
    Observable<HttpResult<List<NavigationListData>>> ramotNavigationUSer();

    @GET("article/list/0/json?")
    Observable<HttpResult<TwoKonwledgehierarchypage>> ramotKonwledgeHierarArticle(@Query("cid")
                                                                                  int cid);

    @GET("tree/json")
    Observable<HttpResult<List<KnowHerarData>>> ramotKonwledgeHierarUser();

    @GET("project/tree/json")
    Observable<HttpResult<List<ProjectAccountsNavigationUser>>>
    ramotProjectAccountsNavifationUser();

    @GET("project/list/{page}/json?")
    Observable<HttpResult<ProjectAccountsArtileData>> ramotProjectAccountsArtileUser
            (@Path("page")int page,@Query("cid")int cid);
//videoCategoryDetails?id=14
    @GET("videoCategoryDetails?id=30")
    Observable<VideoBean> remotVideo();

    //lg/collect/7786/json
    @POST("lg/collect/{id}/json")
    Observable<HttpResult<Data>> onCollect(@Path("id") int id);

}
