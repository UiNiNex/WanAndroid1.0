package wanandroid.c03.jy.com.wanandroid.navigation.JDNavigation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.NavigationReposition;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationContract;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationPresenter;
import wanandroid.c03.jy.com.wanandroid.util.Logger;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class JDNavigation extends BasFragment implements NavigationContract.NavigationView{

    private static final String TAG = "NavigationFragment";

    Unbinder unbinder;
    @BindView(R.id.navigation_left_vtl)
    RecyclerView navigationLeftVtl;
    @BindView(R.id.navigation_split_line)
    ImageView navigationSplitLine;
    @BindView(R.id.navigation_right_rv)
    RecyclerView navigationRightRv;

    private NavigationContract.NavigationPresenter mPresenter;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;
    public boolean mIsNeedScrollRight;


    public JDNavigation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new NavigationPresenter(NavigationReposition.getInstance(getContext()
                .getApplicationContext(),true)));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_jdnavigation, container, false);

        unbinder = ButterKnife.bind(this, inflate);

        navigationRightRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public boolean mIsNeedScrollLeft;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Logger.d("%s scrollState %s",TAG,newState);


                // 只有当用户通过手势滑动右边的时候，才会让左边滚动
                if (newState == RecyclerView.SCROLL_STATE_IDLE ) {

                    if(mIsNeedScrollLeft){
                        mIsNeedScrollLeft = false;
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) navigationRightRv
                                .getLayoutManager();
                        int position = linearLayoutManager.findFirstVisibleItemPosition();
                        View targetView = linearLayoutManager.findViewByPosition(position);

                        float y = targetView.getY();
                        int h = targetView.getHeight();

                        if(Math.abs(y) > h/2){
                            position++;
                        }
                        scrollLeftToPosition((LinearLayoutManager) navigationLeftVtl.getLayoutManager(),
                                position);
                    }

                } else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    mIsNeedScrollLeft = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoadingPage(LoadingPage.MODE_SHOW_BUTTON_AND_TEXT);
        mPresenter.remotNavigationUser();
    }



    @Override
    public void remotNavigationSource(List<NavigationListData> navigationUsers) {
        removeLoadingPage();
        setLeftData(navigationUsers);
        setRightData(navigationUsers);
    }


    private void setRightData(List<NavigationListData> datas) {
        if (mRightAdapter == null) {
            mRightAdapter = new RightAdapter(datas);
            navigationRightRv.setLayoutManager(new LinearLayoutManager(getContext()));
            navigationRightRv.setAdapter(mRightAdapter);
        } else {
            mRightAdapter.setmDatas(datas);
            mLeftAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void remotNavigationError(Throwable e) {
        onError(new LoadingPage.OnReloadCallBack() {
            @Override
            public void reload() {
                loadData();
            }
        }, e.getMessage());
    }
    private void loadData() {
        if (mPresenter != null) {
            mPresenter.remotNavigationUser();
        }
    }

    private void setLeftData(List<NavigationListData> datas) {
        if (mLeftAdapter == null) {
            mLeftAdapter = new LeftAdapter(datas);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            navigationLeftVtl.setLayoutManager(linearLayoutManager);
            navigationLeftVtl.setAdapter(mLeftAdapter);
            mLeftAdapter.select(0);

            mLeftAdapter.setOnSelectListener(new LeftAdapter.OnSelectedListener() {

                @Override
                public void onSelect(int position) {
                    navigationLeftVtl.stopScroll();
                    LinearLayoutManager manager = (LinearLayoutManager)navigationRightRv.getLayoutManager();
                    int fist = manager.findFirstVisibleItemPosition();
                    int last = manager.findLastVisibleItemPosition();
                    if(position <  fist){
                        navigationRightRv.smoothScrollToPosition(position);
                    }else if(position < last){
                        View rightTargetView = navigationRightRv.getLayoutManager().findViewByPosition(position);
                        if(rightTargetView != null){
                            float y = rightTargetView.getTop();
                            if(y > 0){
                                navigationRightRv.smoothScrollBy(0, (int)y);
                            }
                        }
                    }else{
                        Log.e("LZY--------#",(position+1)+":"+(mLeftAdapter.getItemCount()-1));
                        position = Math.min(position+1, mLeftAdapter.getItemCount()-1);
                        mIsNeedScrollRight = true;
                        navigationRightRv.smoothScrollToPosition(position);
                    }

                }
            });
        } else {
            mLeftAdapter.setDatas(datas);
            mLeftAdapter.notifyDataSetChanged();
        }
    }


    private void scrollLeftToPosition(final LinearLayoutManager manager, final int position) {

        View targetView = manager.findViewByPosition(position);

        int fist = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();

        if (targetView == null) {
            navigationLeftVtl.smoothScrollToPosition(position);

            navigationLeftVtl.post(new Runnable() {
                @Override
                public void run() {
                    scrollLeftToPosition(manager, position);
                }
            });
            return;
        }


        int middlePosition = (last + fist) / 2;

        View middleView = manager.findViewByPosition(middlePosition);
        if (middleView == null) {
            return;
        }

        float middleViewY = middleView.getY();

        float targetViewY = targetView.getY();

        float distance = targetViewY - middleViewY;

        if (distance > 0) {
            navigationLeftVtl.smoothScrollBy(0, (int) distance);

        }
        mLeftAdapter.select(position);
    }



    @Override
    public void setPresenter(NavigationContract.NavigationPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext().getApplicationContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
