package wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;

/**
 * User:lenovo
 * Date:2018/12/10
 * Time:9:55
 * author:lzy
 */
public interface KnowledgeHierarchyCotrcte {

    public interface KnowHierarView extends IBaseView<KnowHierarPresenter>{
        int LOAD_TYPE_FIRST = 0;
        int LOAD_TYPE_REFIRST = 1;
        int LOAD_TYPE_MORE = 2;

        void onKnowHierarSuccess(List<KnowHerarData> list,int type);
        void onKnowHierarFail(String message);

        public interface TwoKnowHierarView extends IBaseView<KnowHierarPresenter>{
            void onTwoKnowHierarSucces(List<TwoKonwledgehierarchypage.DatasBean> datas,int type);

            void onTwoKnowHierarFail(String message);
        }

    }

    public interface KnowHierarPresenter extends IBasePresenter<IBaseView> {

        void getKnowHierarSuccess(int type);
        void getTwoKnowHierarSucces(int type);
        void setId(int id);
        void reFreshe();
        void loadmore();

    }

    public interface KnowHierarMode {
        void getKnowHierarInfo(Observer<List<KnowHerarData>> callBack,
                               RxFragment rxFragment);

        void getTwoKnowHierarInfo(Observer<TwoKonwledgehierarchypage> callBack,RxFragment
                rxFragment,int page,int id);

    }

}
