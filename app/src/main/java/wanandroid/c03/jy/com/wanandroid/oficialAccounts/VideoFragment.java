package wanandroid.c03.jy.com.wanandroid.oficialAccounts;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.VideoListAdpter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.VideoDataReposition;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BasFragment implements VideoConuntract.MVideoView{


    @BindView(R.id.kh_recyclerview)
    RecyclerView khRecyclerview;
    @BindView(R.id.kh_samrtrefreshlayout)
    SmartRefreshLayout khSamrtrefreshlayout;
    Unbinder unbinder;
    private VideoPresenter mPresenter;

    List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> mList = new ArrayList<>();
    private VideoListAdpter videoListAdpter;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setPresenter(new VideoPresenter(VideoDataReposition.getInstace(activity)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_official_accounts, container, false);

        unbinder = ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoListAdpter = new VideoListAdpter(mList, getContext());
        khRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        khRecyclerview.setAdapter(videoListAdpter);
        mPresenter.setData();
    }

    @Override
    public void onVideoData(List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> user, int type) {
        if(type == LOAD_TYPE_FIRST){
            mList.addAll(user);
        }

        videoListAdpter.setList(mList);
        videoListAdpter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String message) {

    }

    @Override
    public void setPresenter(VideoConuntract.MVideoPresenter presenter) {
        mPresenter = (VideoPresenter)presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
