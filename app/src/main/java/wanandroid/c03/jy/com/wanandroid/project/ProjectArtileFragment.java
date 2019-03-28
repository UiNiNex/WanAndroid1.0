package wanandroid.c03.jy.com.wanandroid.project;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.MainActivity;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.KnowHierarAdpter;
import wanandroid.c03.jy.com.wanandroid.adepter.ProjectRecyclerAdepter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.ProjectAccountsNavigationUserMode;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;
import wanandroid.c03.jy.com.wanandroid.home.MassageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectArtileFragment extends BasFragment implements ProjectAccountsConuntract.ProjectAccountsView {

    @BindView(R.id.kh_recyclerview)
    RecyclerView khRecyclerview;
    @BindView(R.id.kh_samrtrefreshlayout)
    SmartRefreshLayout khSamrtrefreshlayout;
    Unbinder unbinder;
    private ProjectAccountsConuntract.ProjectAccountsPresenter mPresenter;

    private List<ProjectAccountsArtileData.DatasBean> list = new ArrayList<>();
    private ProjectRecyclerAdepter projectRecyclerAdepter;


    public ProjectArtileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new ProjectPresenter(ProjectAccountsNavigationUserMode.getInstance
                (getContext(),true)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_project_artile, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, inflate);
        khSamrtrefreshlayout.setRefreshHeader(new DropBoxHeader(getContext()));
        khSamrtrefreshlayout.setEnableLoadMore(true);
        khSamrtrefreshlayout.setEnableRefresh(true);
        khSamrtrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onLodmore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle arguments = getArguments();
        int cid = arguments.getInt("cid");
        mPresenter.serId(cid);
        mPresenter.remotProjectAccountsArtileData(ProjectAccountsConuntract.ProjectAccountsView.LOAD_TYPE_FIRST);

        projectRecyclerAdepter = new ProjectRecyclerAdepter(list, getContext());
        projectRecyclerAdepter.setAnimation(new KnowHierarAdpter.SlideInRightAnimation());
        khRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        khRecyclerview.setAdapter(projectRecyclerAdepter);
        hdieBottom(khRecyclerview,getActivity());

        projectRecyclerAdepter.setmProjectOnclick(new ProjectRecyclerAdepter.ProjectOnclick() {
            @Override
            public void onClick(AutoMassage autoMassage) {
                EventBus.getDefault().postSticky(autoMassage);
                startActivity(new Intent(getActivity(), MassageActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomBanner(String s){}
    @Override
    public void onProjectAccountsNavigationUser(List<ProjectAccountsNavigationUser> mUser, int type) {

    }

    @Override
    public void onProjectAccountsAricleData(ProjectAccountsArtileData datas, int type) {

        if(type == LOAD_TYPE_FIRST){
            list.clear();
        }else if(type == LOAD_TYPE_REFIRST){
            khSamrtrefreshlayout.finishRefresh(1000,true);
            list.clear();
        }else{
            khSamrtrefreshlayout.finishLoadMore(true);
        }

        list.addAll(datas.getDatas());
        projectRecyclerAdepter.setList(list);
        projectRecyclerAdepter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String message) {

    }

    @Override
    public void setPresenter(ProjectAccountsConuntract.ProjectAccountsPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }

    public void setRecyclerToTop(){
        khRecyclerview.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
