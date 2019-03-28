package wanandroid.c03.jy.com.wanandroid.navigation;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.NavigationAdapter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.NavigationReposition;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BasFragment{


    Unbinder unbinder;
    @BindView(R.id.navigation_tab)
    VerticalTabLayout navigationTab;
    @BindView(R.id.view_navigation)
    View viewNavigation;
    @BindView(R.id.navigation_recycler)
    RecyclerView navigationRecycler;

    private NavigationContract.NavigationPresenter mNavigationPresenter;
    private LinearLayoutManager layoutManager;
    private NavigationAdapter navigationAdapter;

    private List<NavigationUser> list;
    private boolean needScroll;
    private boolean isClickTab;
    private int index;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new NavigationPresenter(NavigationReposition.getInstance(getContext(),true)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());

        navigationRecycler.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        navigationAdapter = new NavigationAdapter(list);

        navigationRecycler.setAdapter(navigationAdapter);

        mNavigationPresenter.remotNavigationUser();

        hdieBottom(navigationRecycler,getActivity());

    }

//    @Override
    public void remotNavigationError(Throwable e) {

    }



//    @Override
    public void remotNavigationSource(List<NavigationUser> navigationUsers) {

                list.addAll(navigationUsers);

                navigationAdapter.notifyDataSetChanged();


                navigationTab.setTabAdapter(new TabAdapter() {
                    @Override
                    public int getCount() {
                        return list == null ? 0 : list.size();
                    }

                    @Override
                    public ITabView.TabBadge getBadge(int position) {
                        return null;
                    }

                    @Override
                    public ITabView.TabIcon getIcon(int position) {
                        return null;
                    }

                    @Override
                    public ITabView.TabTitle getTitle(int position) {
                        return new ITabView.TabTitle.Builder()
                                .setContent(list.get(position).getName())
                                .setTextColor(
                                        ContextCompat.getColor(getActivity(), R.color.navigation_tabselect),
                                        ContextCompat.getColor(getActivity(), R.color.black_text))
                                .build();
                    }

                    @Override
                    public int getBackground(int position) {
                        return -1;
                    }
                });

                navigationAdapter.setNewData(list);
                leftRightLinkage();

    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - layoutManager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < navigationRecycler.getChildCount()) {
            int top = navigationRecycler.getChildAt(indexDistance).getTop();
            navigationRecycler.smoothScrollBy(0, top);
        }
    }

    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = layoutManager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (navigationTab == null) {
                return;
            }
            navigationTab.setTabSelected(index);
        }
        index = position;
    }

    private void selectTag(int i) {
        index = i;
        navigationRecycler.stopScroll();
        smoothScrollToPosition(i);
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            navigationRecycler.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = navigationRecycler.getChildAt(currentPosition - firstPosition).getTop();
            navigationRecycler.smoothScrollBy(0, top);
        } else {
            navigationRecycler.smoothScrollToPosition(currentPosition);
            needScroll = true;
        }
    }

    private void leftRightLinkage() {
        navigationRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        navigationTab.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

//    @Override
    public void setPresenter(NavigationContract.NavigationPresenter presenter) {
        mNavigationPresenter = presenter;
//        mNavigationPresenter.attachView(this);
    }

//    @Override
    public Context getContextObject() {
        return getContext();
    }

    public void setRecyclerToTop(){
        navigationRecycler.smoothScrollToPosition(0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mNavigationPresenter.detachView();
    }

}
