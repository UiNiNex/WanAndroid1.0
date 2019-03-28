package wanandroid.c03.jy.com.wanandroid.adepter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import wanandroid.c03.jy.com.wanandroid.GlideApp;
import wanandroid.c03.jy.com.wanandroid.GlideRequest;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;

/*
 * created by taofu on 2018/12/5
 **/

public class HomeAdapter extends IBasRecyclerAdepter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0x100;
    private static final int TYPE_ARTICLE = 0x200;


    private List<HomeUser> mArticles;
    private GlideRequest<Drawable> mImageRequest;
    private View mBanner;

    public HomeAdapter() {
        super();
    }

    public HomeAdapter(List<HomeUser> mArticles, GlideRequest<Drawable> mImageRequest, View mBanner) {
        this.mArticles = mArticles;
        this.mImageRequest = mImageRequest;
        this.mBanner = mBanner;
    }

    public void setData(List<HomeUser> datas){
        mArticles = datas;

    }

    public void addHeader(View view){
        mBanner = view;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(mImageRequest == null){
            mImageRequest = GlideApp.with(viewGroup).asDrawable();
        }
        if(i == TYPE_HEADER){
            return new HomeHeaderViewHolder(mBanner);
        }else{
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_article, viewGroup, false);
            HomeArticleViewHolder viewHolder = new HomeArticleViewHolder(itemView);
            return viewHolder;
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if(viewHolder instanceof HomeArticleViewHolder) {

            ((HomeArticleViewHolder) viewHolder).setData((mBanner == null ? mArticles.get(i) : mArticles.get(i - 1)));

            (((HomeArticleViewHolder) viewHolder)).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHomePageOnClicks != null) {
                        if(mBanner!= null){
                            mHomePageOnClicks.onClick(mArticles.get(i-1));
                        }else{
                            mHomePageOnClicks.onClick(mArticles.get(i));
                        }

                    }
                }
            });

            (((HomeArticleViewHolder) viewHolder)).mIsLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mHomeCollectOnClicks != null){
                        mHomeCollectOnClicks.onClick(Integer.parseInt(mArticles.get(i-1).getId()));
                    }
                }
            });
        }
        if(mOpenAnimationEnable){
            addAnimation(viewHolder);
        }

    }

    @Override
    public int getItemViewType(int position) {
         if(mBanner != null && position == 0) {
             return TYPE_HEADER;
         } else{
             return TYPE_ARTICLE;
         }

    }

    @Override
    public int getItemCount() {
        int listSize = mArticles == null ? 0 : mArticles.size();
        return mBanner == null ? listSize : listSize + 1;
    }

    public class HomeHeaderViewHolder extends  RecyclerView.ViewHolder{

        public HomeHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class HomeArticleViewHolder extends RecyclerView.ViewHolder{

        private TextView mIsNew;
        TextView mTag;
        private TextView mAuthor;

        private TextView mTime;
        private TextView mTitle;
        private TextView mChapterName;
        private ImageView mPic;
        private CheckBox mIsLike;

        View item;

        public HomeArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView;
            mIsNew = itemView.findViewById(R.id.home_item_is_new);
            mTag = itemView.findViewById(R.id.home_item_tag);


            mAuthor = itemView.findViewById(R.id.home_item_author);
            mTime = itemView.findViewById(R.id.home_item_time);
            mPic = itemView.findViewById(R.id.home_item_pic);
            mTitle = itemView.findViewById(R.id.home_item_title);
            mChapterName = itemView.findViewById(R.id.home_item_chapter_name);
            mIsLike = itemView.findViewById(R.id.home_item_collection);
        }

        private void setData(HomeUser data){

            // 是否为新的
            if(data.isFresh()){
                mIsNew.setVisibility(View.VISIBLE);
                mIsNew.setText(R.string.text_new);
            }else{
                mIsNew.setVisibility(View.GONE);
            }

            // 是否显示tag
            if(data.getTags() != null && data.getTags().size() > 0){
                mTag.setVisibility(View.VISIBLE);
                mTag.setText(data.getTags().get(0).getName());
                mTag.setTag(data.getTags().get(0).getUrl());
            }else{
                mTag.setVisibility(View.GONE);
            }

            mAuthor.setText(data.getAuthor());

            mTime.setText(data.getNiceDate());

            mTitle.setText(data.getTitle());

            mChapterName.setText(data.getChapterName());

            mIsLike.setChecked(data.isCollect());


            if(TextUtils.isEmpty(data.getEnvelopePic())){
                mPic.setVisibility(View.GONE);
            }else{
                mPic.setVisibility(View.VISIBLE);
                mImageRequest.load(data.getEnvelopePic()).into(mPic);
            }

        }

        public void setOnClick(){

            String isNew = mIsNew.getText().toString();
            String tag = mTag.getText().toString();
            String author = mAuthor.getText().toString();
            String time = mTime.getText().toString();

        }
    }

    private HomePageOnClicks mHomePageOnClicks;

    public void setmHomePageOnClicks(HomePageOnClicks mHomePageOnClicks) {
        this.mHomePageOnClicks = mHomePageOnClicks;
    }

    public interface HomePageOnClicks{
        void onClick(HomeUser mArticles);
    }

    private  HomeCollectOnClicks mHomeCollectOnClicks;

    public void setmHomeCollectOnClicks(HomeCollectOnClicks mHomeCollectOnClicks) {
        this.mHomeCollectOnClicks = mHomeCollectOnClicks;
    }

    public interface HomeCollectOnClicks{
        void onClick(int id);
    }

}
