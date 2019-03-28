package wanandroid.c03.jy.com.wanandroid.adepter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;


/**
 * User:lenovo
 * Date:2018/12/10
 * Time:20:11
 * author:lzy
 */
public class KnowHierarAdpter extends IBasRecyclerAdepter<KnowHierarAdpter.ViewHolder> {

    private List<KnowHerarData> list;

    private LayoutInflater inflater;
    private int mLastPosition = -1;
    private int mPosition = 0;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private BaseAnimation mSelectAnimation = new SlideInRightAnimation();
    private boolean mOpenAnimationEnable = true;
    private View inflate;


    public void setList(List<KnowHerarData> list) {
        this.list = list;
    }

    public KnowHierarAdpter() {
        super();
    }

    public KnowHierarAdpter(List<KnowHerarData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kh_rec_item_carview,
                viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        viewHolder1.kh_home_item_title.setText(list.get(i).getName());
        List<KnowHerarData.ChildrenBean> children = list.get(i).getChildren();
        StringBuffer stringBuffer = new StringBuffer();
        for (KnowHerarData.ChildrenBean child : children) {
            stringBuffer.append(child.getName() + "\u3000");
        }

        viewHolder1.kh_rec_item_sourrce.setText(stringBuffer.toString());

            addAnimation(
                    viewHolder
            );



        viewHolder1.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mKnowHierarPageOnClicks != null) {
                    mKnowHierarPageOnClicks.onClick(list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView kh_home_item_title;
        public TextView kh_rec_item_sourrce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.kh_home_item_title = (TextView) itemView.findViewById(R.id.kh_home_item_title);
            this.kh_rec_item_sourrce = (TextView) itemView.findViewById(R.id.kh_rec_item_sourrce);
        }

    }

    private KnowHieraraPageOnClicks mKnowHierarPageOnClicks;

    public void setKnowHieraraPageOnClicks(KnowHieraraPageOnClicks mKnowHierarPageOnClicks) {
        this.mKnowHierarPageOnClicks = mKnowHierarPageOnClicks;
    }

    public interface KnowHieraraPageOnClicks {
        void onClick(KnowHerarData mKnowHerarData);
    }


}

