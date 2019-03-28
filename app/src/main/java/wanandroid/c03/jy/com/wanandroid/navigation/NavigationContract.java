package wanandroid.c03.jy.com.wanandroid.navigation;

import android.view.View;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;

/**
 * @author quchao
 * @date 2018/2/11
 */

public interface NavigationContract {

    interface NavigationPresenter extends IBasePresenter<NavigationView>{

        void remotNavigationUser();
    }

    interface NavigationView extends IBaseView<NavigationPresenter>{
        int LOAD_TYPE_FIRST = 0;
        int LOAD_TYPE_REFIRST = 1;
        int LOAD_TYPE_MORE = 2;

        void remotNavigationError(Throwable e);
        void remotNavigationSource(List<NavigationListData> navigationUsers);
    }

    interface  NavigationMode{
        void onRemotNavigationUser(Observer<List<NavigationListData>> callBack, RxFragment rxFragment);
    }
}
