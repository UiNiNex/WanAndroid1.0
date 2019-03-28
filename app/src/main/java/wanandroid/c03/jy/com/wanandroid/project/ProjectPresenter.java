package wanandroid.c03.jy.com.wanandroid.project;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.ProjectAccountsNavigationUserMode;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;

/**
 * User:lenovo
 * Date:2018/12/12
 * Time:21:15
 * author:lzy
 */
public class ProjectPresenter implements ProjectAccountsConuntract.ProjectAccountsPresenter {

    private ProjectAccountsNavigationUserMode mMode;
    private ProjectAccountsConuntract.ProjectAccountsView mView;

    private int page;
    private int mId;

    public ProjectPresenter(ProjectAccountsNavigationUserMode mMode){
        this.mMode = mMode;
    }


    @Override
    public void remotProjectAccountsNavigationUSer(final int type) {

        mMode.onRemotProjectUser(new Observer<List<ProjectAccountsNavigationUser>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ProjectAccountsNavigationUser> projectAccountsNavigationUsers) {
                mView.onProjectAccountsNavigationUser(projectAccountsNavigationUsers,type);
            }

            @Override
            public void onError(Throwable e) {
                mView.onFile(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment)mView);
    }

    @Override
    public void remotProjectAccountsArtileData(final int type) {
        mMode.onRemotProjectArticleList(new Observer<ProjectAccountsArtileData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ProjectAccountsArtileData projectAccountsArtileData) {
                page = projectAccountsArtileData.getCurPage();
                mView.onProjectAccountsAricleData(projectAccountsArtileData,type);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },(RxFragment)mView,page,mId);
    }

    @Override
    public void refresh() {
        page=0;
        remotProjectAccountsArtileData(mView.LOAD_TYPE_REFIRST);
    }

    @Override
    public void onLodmore() {
        page++;
        remotProjectAccountsArtileData(mView.LOAD_TYPE_MORE);
    }

    @Override
    public void serId(int id) {
        mId = id;
    }

    @Override
    public void attachView(ProjectAccountsConuntract.ProjectAccountsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
