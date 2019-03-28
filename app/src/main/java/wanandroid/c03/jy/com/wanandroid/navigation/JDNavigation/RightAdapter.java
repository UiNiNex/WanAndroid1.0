package wanandroid.c03.jy.com.wanandroid.navigation.JDNavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.Article;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;
import wanandroid.c03.jy.com.wanandroid.util.SystemFacade;

/**
 * User:lenovo
 * Date:2018/12/19
 * Time:21:48
 * author:lzy
 */
public class RightAdapter extends RecyclerView.Adapter<RightAdapter.RightViewHolder>{

    private List<NavigationListData> mDatas;

    public RightAdapter(List<NavigationListData> datas) {
        this.mDatas = datas;
    }

    public void setmDatas(List<NavigationListData> mDatas) {
        this.mDatas = mDatas;
    }

    private SparseArray<TextView> mTextViewSparseArray = new SparseArray<>();

    @NonNull
    @Override
    public RightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RightViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_navigation_right, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RightViewHolder rightViewHolder, int i) {
        rightViewHolder.setData(mDatas.get(i),i);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }




    public class RightViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TagFlowLayout mTflContnet;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.navigation_tv_right_title);
            mTflContnet = itemView.findViewById(R.id.navigation_flow_layout_right_content);
        }
        public void setData(NavigationListData data,final int itemPosition){

            if(TextUtils.isEmpty(data.getName())){
                mTvTitle.setText(R.string.text_unknow);
            }else{
                mTvTitle.setText(data.getName());
            }
            if(data.getArticles() == null || data.getArticles().size() == 0){
                mTflContnet.setVisibility(View.GONE);
            }else{
                mTflContnet.setVisibility(View.VISIBLE);
            }
            mTflContnet.setAdapter(new TagAdapter<Article>(data.getArticles()) {
                @Override
                public View getView(FlowLayout parent, int position, Article article) {
                    int index = 0;
                    if (itemPosition == 0) {
                        index = position;
                    } else {
                        int m = itemPosition;
                        while (m > 0) {
                            index += mDatas.get(--m).getArticles().size();
                        }
                        index += position;
                    }
                    Log.e("Test", "-------------- P = " + itemPosition + "  p = " + position + " index =" + index);
                    TextView tagView = mTextViewSparseArray.get(index);
                    if (tagView == null) {
                        Context context = parent.getContext();
                        tagView = new TextView(parent.getContext());
                        int h = SystemFacade.dp2px(context, 10);
                        int w = SystemFacade.dp2px(context, 6);
                        tagView.setPadding(h, w, h, w);
                        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(w, w, w, w);
                        tagView.setLayoutParams(layoutParams);
                        tagView.setBackgroundResource(R.drawable.navigation_right_child_item_tag_bg);
                        tagView.setText(article.getTitle());
                        tagView.setTextColor(CommonUtils.randomColor());
                        mTextViewSparseArray.put(index, tagView);

                    } else {

                        ((ViewGroup) tagView.getParent()).removeView(tagView);
                        Context context = parent.getContext();
                        int h = SystemFacade.dp2px(context, 10);
                        int w = SystemFacade.dp2px(context, 6);
                        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(w, w, w, w);
                        tagView.setLayoutParams(layoutParams);
                    }

                    return tagView;
                }
            });
        }
    }

}
