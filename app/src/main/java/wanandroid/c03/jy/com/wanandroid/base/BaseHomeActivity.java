package wanandroid.c03.jy.com.wanandroid.base;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import wanandroid.c03.jy.com.wanandroid.home.HomeFragment;
import wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy.KnowledgeHierarchyFragment;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationFragment;
import wanandroid.c03.jy.com.wanandroid.project.ProjectFragment;

/**
 * User:lenovo
 * Date:2019/1/12
 * Time:14:46
 * author:lzy
 */
public class BaseHomeActivity extends BaseLoginActivity{

    public void fabOnClick(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getFragment();
                if(fragment instanceof HomeFragment){
                    HomeFragment mHomeFragment = (HomeFragment) fragment;
                    mHomeFragment.setRecyclerToTop();
                    return;
                }
                if(fragment instanceof KnowledgeHierarchyFragment){
                    KnowledgeHierarchyFragment mHomeFragment = (KnowledgeHierarchyFragment) fragment;
                    mHomeFragment.setRecyclerToTop();
                    return;
                }
                if(fragment instanceof NavigationFragment){
                    NavigationFragment mHomeFragment = (NavigationFragment) fragment;
                    mHomeFragment.setRecyclerToTop();
                    return;
                }
                if(fragment instanceof ProjectFragment){
                    ProjectFragment mHomeFragment = (ProjectFragment) fragment;
                    mHomeFragment.setRecyclerToTop();
                    return;
                }
            }
        });
    }

}
