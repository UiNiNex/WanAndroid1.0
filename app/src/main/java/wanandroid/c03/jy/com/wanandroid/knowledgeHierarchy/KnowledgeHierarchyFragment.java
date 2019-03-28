package wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy;


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
import wanandroid.c03.jy.com.wanandroid.MainActivity;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.KnowHierarAdpter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.KnowHierarRepository;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;

/**
 * A simple {@link Fragment} subclass.
 */
public class  KnowledgeHierarchyFragment extends BasFragment implements KnowledgeHierarchyCotrcte.KnowHierarView {

    @BindView(R.id.kh_recyclerview)
    RecyclerView khRecyclerview;
    @BindView(R.id.kh_samrtrefreshlayout)
    SmartRefreshLayout khSamrtrefreshlayout;
    Unbinder unbinder;

    private List<KnowHerarData> mKnowHerarDatas;
    private KnowledgeHierarchyCotrcte.KnowHierarPresenter mPresenter;
    private KnowHierarAdpter knowHierarAdpter;

    public KnowledgeHierarchyFragment() {
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
        View inflate = inflater.inflate(R.layout.fragment_knowledge_hierarchy, container, false);

        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hdieBottom(khRecyclerview,getActivity());
        mPresenter.getKnowHierarSuccess(KnowledgeHierarchyCotrcte.KnowHierarView.LOAD_TYPE_FIRST);

        if(knowHierarAdpter == null){

            knowHierarAdpter = new KnowHierarAdpter();
        }
        knowHierarAdpter.setKnowHieraraPageOnClicks((MainActivity)getContext());

    }

    @Override
    public void onKnowHierarSuccess(List<KnowHerarData> list, int type) {

        if(mKnowHerarDatas == null){
            mKnowHerarDatas = new ArrayList<>();
        }

        if (type == LOAD_TYPE_FIRST) {
            mKnowHerarDatas.clear();
        } else if (type == LOAD_TYPE_REFIRST) {
            khSamrtrefreshlayout.finishRefresh(1000, true);
            mKnowHerarDatas.clear();
        } else if (type == LOAD_TYPE_MORE) {
            khSamrtrefreshlayout.finishLoadMore(true);
        }

        mKnowHerarDatas.addAll(list);

        if(knowHierarAdpter == null){
            knowHierarAdpter = new KnowHierarAdpter();
            knowHierarAdpter.setList(mKnowHerarDatas);
            khRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            khRecyclerview.setAdapter(knowHierarAdpter);
        }else{
            knowHierarAdpter.setList(mKnowHerarDatas);
            khRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            khRecyclerview.setAdapter(knowHierarAdpter);
            knowHierarAdpter.notifyDataSetChanged();
        }

    }

    @Override
    public void onKnowHierarFail(String message) {

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

    public void setRecyclerToTop(){
        khRecyclerview.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        mPresenter.detachView();
        unbinder.unbind();
    }
}
