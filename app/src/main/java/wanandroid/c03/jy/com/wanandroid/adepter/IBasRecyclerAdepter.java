package wanandroid.c03.jy.com.wanandroid.adepter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * User:lenovo
 * Date:2018/12/13
 * Time:12:51
 * author:lzy
 */
abstract class IBasRecyclerAdepter<T> extends RecyclerView.Adapter{

        private LayoutInflater inflater;
        private int mLastPosition = -1;
        private int mPosition = 0;
        private Interpolator mInterpolator = new LinearInterpolator();
        private int mDuration =800;
        private KnowHierarAdpter.BaseAnimation mSelectAnimation = new KnowHierarAdpter.SlideInRightAnimation();
        public boolean mOpenAnimationEnable = true;
        private View inflate;
        private KnowHierarAdpter.BaseAnimation animation;

        /**
         * 添加动画
         *
         * @param holder
         */
        public void addAnimation(RecyclerView.ViewHolder holder) {
            if (mOpenAnimationEnable) {
                if (holder.getLayoutPosition() > mLastPosition) {
                    animation = null;
                    if (mSelectAnimation != null) {
                        animation = mSelectAnimation;
                    }

                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        startAnim(anim);
                    }
                    mLastPosition = holder.getLayoutPosition();
                }
            }
        }

        /**
         * 开启动画
         *
         * @param animator
         */
        private void startAnim(Animator animator) {
            animator.setDuration(mDuration).start();
            animator.setInterpolator(mInterpolator);
        }

        /**
         * 设置动画效果
         *
         * @param animation
         */
        public void setAnimation(KnowHierarAdpter.BaseAnimation animation) {
            this.mOpenAnimationEnable = true;
            this.mSelectAnimation = animation;
        }


        public static class SlideInRightAnimation implements KnowHierarAdpter.BaseAnimation {

            @Override
            public Animator[] getAnimators(View view) {
                ObjectAnimator intoAnimator = ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(),
                        0);
                ObjectAnimator outoIntoAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                ObjectAnimator bigIntoAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
                ObjectAnimator bigsIntoAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
                return new Animator[]{
                        intoAnimator,
                        outoIntoAnimator,
                    bigIntoAnimator,
                    bigsIntoAnimator
            };
        }
    }

    public interface BaseAnimation {

        Animator[] getAnimators(View view);

    }
}
