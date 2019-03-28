package wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.TwoKHRecyclerAdpter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.KnowHierarRepository;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReuseFragments extends BasFragment implements KnowledgeHierarchyCotrcte.KnowHierarView
        .TwoKnowHierarView {

    private KnowledgeHierarchyCotrcte.KnowHierarPresenter mPresenter;

    @BindView(R.id.kh_recyclerview)
    RecyclerView khRecyclerview;
    @BindView(R.id.kh_samrtrefreshlayout)
    SmartRefreshLayout khSamrtrefreshlayout;
    Unbinder unbinder;
    private int twoId;
    private TwoKHRecyclerAdpter twoKHRecyclerAdpter;
    private List<TwoKonwledgehierarchypage.DatasBean> list = new ArrayList<>();
    private int mId = 0;

    public ReuseFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new KnowHierarPresenter(KnowHierarRepository.getInstance(getContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_reuse_fragments, container, false);

        unbinder = ButterKnife.bind(this, inflate);
        Bundle arguments = getArguments();
        mId = arguments.getInt("id");
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        twoKHRecyclerAdpter = new TwoKHRecyclerAdpter(list);
        khRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        khRecyclerview.setAdapter(twoKHRecyclerAdpter);
        mPresenter.setId(mId);
        mPresenter.getTwoKnowHierarSucces(KnowledgeHierarchyCotrcte.KnowHierarView.LOAD_TYPE_FIRST);
        twoKHRecyclerAdpter.setmTwoKHOnClike((FragmentsMessageActivity)getContext());
        khSamrtrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadmore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.reFreshe();
            }
        });
    }

    @Override
    public void onTwoKnowHierarSucces(List<TwoKonwledgehierarchypage.DatasBean> datas, int type) {
        list.addAll(datas);
        twoKHRecyclerAdpter.setList(list);
        twoKHRecyclerAdpter.notifyDataSetChanged();
    }

    @Override
    public void onTwoKnowHierarFail(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(KnowledgeHierarchyCotrcte.KnowHierarPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}
