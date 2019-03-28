package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.oficialAccounts.VideoConuntract;

/**
 * User:lenovo
 * Date:2018/12/26
 * Time:11:13
 * author:lzy
 */
public class VideoDataReposition implements VideoConuntract.VideoMode{

    private static volatile VideoDataReposition mInstance;
    private WARetrofitService mService;

    private VideoDataReposition (Context context){
        mService = DataService.getVideoService(context);
    }

    public static VideoDataReposition getInstace(Context context){
        if(mInstance == null){
            synchronized (VideoDataReposition.class){
                if(mInstance == null){
                    mInstance = new VideoDataReposition(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onVideoData(Observer<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>> callBack, RxFragment rxFragment,
                            int id) {
        mService.remotVideo()
                .flatMap(new Function<VideoBean, ObservableSource<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>>>() {
                    @Override
                    public ObservableSource<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>> apply(VideoBean videoBean) throws Exception {
                        if(videoBean.getCode() == 200){
                            if(videoBean.getResult() != null && videoBean.getResult().size() > 0){
                                List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> dataBeans = new ArrayList<>();
                                List<VideoBean.ResultBean> result = videoBean.getResult();
                                for (VideoBean.ResultBean resultBean : result) {
                                    dataBeans.add(resultBean.getData().getContent().getData());
                                }
                                return Observable.just(dataBeans);
                            }
                        }
                        return Observable.error(ExceptionManager.buildServerErrorMessage
                                (videoBean.getCode(),videoBean.getMessage()));
                    }
                }).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(rxFragment.<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>>bindUntilEvent(FragmentEvent.DETACH))
                    .subscribe(callBack);


    }
}
