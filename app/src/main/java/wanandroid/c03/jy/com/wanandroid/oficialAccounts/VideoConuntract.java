package wanandroid.c03.jy.com.wanandroid.oficialAccounts;

import android.widget.VideoView;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;

/**
 * User:lenovo
 * Date:2018/12/12
 * Time:19:32
 * author:lzy
 */
public interface VideoConuntract {

    public interface MVideoView extends IBaseView<MVideoPresenter>{
        int LOAD_TYPE_FIRST = 0;
        int LOAD_TYPE_REFIRST = 1;
        int LOAD_TYPE_MORE = 2;

        void onVideoData(List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> user, int type);

        void onFile(String message);
    }

    public interface MVideoPresenter extends IBasePresenter<MVideoView>{

        void remotVideoUSer(int type);

        void refresh();
        void onLodmore();

    }

    public interface VideoMode{

        void onVideoData(Observer<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>> callBack, RxFragment
                rxFragment, int id);


    }

}
