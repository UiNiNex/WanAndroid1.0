package wanandroid.c03.jy.com.wanandroid.project;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;

/**
 * User:lenovo
 * Date:2018/12/12
 * Time:19:32
 * author:lzy
 */
public interface ProjectAccountsConuntract {

    public interface ProjectAccountsView extends IBaseView<ProjectAccountsPresenter>{
        int LOAD_TYPE_FIRST = 0;
        int LOAD_TYPE_REFIRST = 1;
        int LOAD_TYPE_MORE = 2;

        void onProjectAccountsNavigationUser(List<ProjectAccountsNavigationUser> mUser, int type);

        void onProjectAccountsAricleData(ProjectAccountsArtileData data, int type);

        void onFile(String message);
    }

    public interface ProjectAccountsPresenter extends IBasePresenter<ProjectAccountsView>{

        void remotProjectAccountsNavigationUSer(int type);
        void remotProjectAccountsArtileData(int type);

        void refresh();
        void onLodmore();

        void serId(int id);

    }

    public interface ProjectMode{

        void onRemotProjectUser(Observer<List<ProjectAccountsNavigationUser>> callBack, RxFragment
                rxFragment);
        void onRemotProjectArticleList(Observer<ProjectAccountsArtileData> callBack, RxFragment rxFragment,
                                    int page, int id);

    }

}
