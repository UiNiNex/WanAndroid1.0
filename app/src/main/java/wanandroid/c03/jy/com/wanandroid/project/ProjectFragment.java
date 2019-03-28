package wanandroid.c03.jy.com.wanandroid.project;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.ProjectViewPagerAdapter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.ProjectAccountsNavigationUserMode;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BasFragment implements ProjectAccountsConuntract.ProjectAccountsView {


    @BindView(R.id.project_viewpager)
    ViewPager projectViewpager;
    Unbinder unbinder;
    @BindView(R.id.project_tablayout)
    TabLayout projectTablayout;
    private ProjectAccountsConuntract.ProjectAccountsPresenter mPresenter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> title;
    private List<ProjectAccountsNavigationUser> mUser;
    private Bundle bundle;

    private int position = 0;

    public ProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new ProjectPresenter(ProjectAccountsNavigationUserMode.getInstance
                (getContext(), true)));
    }

    //    飞机
//        homeSmartrefreshlayout.setRefreshHeader(new TaurusHeader(getContext()));
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_project, container, false);

        unbinder = ButterKnife.bind(this, inflate);

        return inflate;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.remotProjectAccountsNavigationUSer(ProjectAccountsConuntract
                .ProjectAccountsView.LOAD_TYPE_FIRST);


        projectViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                position = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onProjectAccountsNavigationUser(List<ProjectAccountsNavigationUser> mUser, int type) {
       this.mUser = mUser;


        fragments = new ArrayList<>();
        title = new ArrayList<>();

        for (int i = 0; i < mUser.size(); i++) {
            ProjectArtileFragment projectArtileFragment = new ProjectArtileFragment();
            bundle = new Bundle();
            bundle.putInt("cid", mUser.get(i).getId());
            projectArtileFragment.setArguments(bundle);
            fragments.add(projectArtileFragment);
            title.add(mUser.get(i).getName());
        }

        ProjectViewPagerAdapter projectViewPagerAdapter = new ProjectViewPagerAdapter(getChildFragmentManager(), fragments, title);

        projectViewpager.setAdapter(projectViewPagerAdapter);
        projectTablayout.setupWithViewPager(projectViewpager);

    }

    @Override
    public void onProjectAccountsAricleData(ProjectAccountsArtileData datas, int type) {

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

    public void  setRecyclerToTop(){
        if(fragments != null){
            Fragment fragment = fragments.get(position);
            ProjectArtileFragment fragment1 = (ProjectArtileFragment) fragment;
            fragment1.setRecyclerToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        unbinder.unbind();
    }
}
