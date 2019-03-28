package wanandroid.c03.jy.com.wanandroid.adepter;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;
import wanandroid.c03.jy.com.wanandroid.oficialAccounts.VideoFragment;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

/**
 * User:lenovo
 * Date:2018/12/26
 * Time:14:01
 * author:lzy
 */
public class VideoListAdpter extends IBasRecyclerAdepter<RecyclerView.ViewHolder> {
    private List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> list;
    private Context mContext;
    public MyViewHolder viewHolder1;
    private ViewGroup viewGroup;

    public VideoListAdpter(List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> list,
                           Context context) {
        this.list = list;
        mContext = context;
    }

    public void setList(List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item_recucler,
                viewGroup, false);
        this.viewGroup = viewGroup;
        MyViewHolder myViewHolder = new MyViewHolder(inflate);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        viewHolder1 = (MyViewHolder) viewHolder;

        JzvdStd mJzvdStd = viewHolder1.mJzvdStd;
        viewHolder1.setmJzvdStds(list.get(i));

        if (mOpenAnimationEnable) {
            addAnimation(viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        private SensorManager sensorManager;
        private JzvdStd mJzvdStd;
        //        private Jzvd.JZAutoFullscreenListener sensorEventListener;

        public MyViewHolder(@NonNull View rootView) {
            super(rootView);
            rootView = rootView;
            mJzvdStd = rootView.findViewById(R.id.videoplayers);
        }

        public void setmJzvdStds(VideoBean.ResultBean.DataBeanX.ContentBean.DataBean dataBean){

            mJzvdStd.backButton.setVisibility(View.GONE);
            mJzvdStd.setUp(dataBean.getPlayUrl()
                    , dataBean.getTitle() , Jzvd.SCREEN_WINDOW_LIST);
            Glide.with(mContext).load(dataBean.getCover().getFeed()).into(mJzvdStd
                    .thumbImageView);

            //用于实现重力感应下切换横竖屏
            sensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
//            sensorEventListener = new JzvdStd(mContext).JZAutoFullscreenListener();
            JzvdStd.FULLSCREEN_ORIENTATION = SCREEN_ORIENTATION_LANDSCAPE;  //横向
            JzvdStd.NORMAL_ORIENTATION = SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
//           jzVideoPlayer.ba
//            jzVideoPlayer.setUp(dataBean.getPlayUrl()
//                    , JZVideoPlayerStandard.SCREEN_WINDOW_LIST, dataBean.getTitle());
////            Glide.with(mContext).load(dataBean.getCover().getFeed()).into(jzVideoPlayer.thumbImageView);
//
//            //跳转制定位置播放
////        JZMediaManager.seekTo(30000);
////            lsy_video_player.startWindowTiny();//小窗播放
            JzvdStd.SAVE_PROGRESS = true;//保存播放进度
        }

        public void startPlayer(){
            mJzvdStd.onStatePlaying();
        }

        public void stopPlayer(){
            mJzvdStd.onStatePause();
        }

    }

}
