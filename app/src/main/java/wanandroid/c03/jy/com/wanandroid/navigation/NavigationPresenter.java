package wanandroid.c03.jy.com.wanandroid.navigation;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;

/**
 * User:lenovo
 * Date:2018/12/15
 * Time:19:47
 * author:lzy
 */
public class NavigationPresenter implements NavigationContract.NavigationPresenter{

    private NavigationContract.NavigationMode mMode;
    private NavigationContract.NavigationView mView;

    public NavigationPresenter(NavigationContract.NavigationMode mode){
        mMode = mode;
    }

    @Override
    public void remotNavigationUser() {
        mMode.onRemotNavigationUser(new Observer<List<NavigationListData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NavigationListData> navigationUsers) {
                mView.remotNavigationSource(navigationUsers);

            }

            @Override
            public void onError(Throwable e) {
                mView.remotNavigationError(e);
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment) mView);
    }

    @Override
    public void attachView(NavigationContract.NavigationView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
