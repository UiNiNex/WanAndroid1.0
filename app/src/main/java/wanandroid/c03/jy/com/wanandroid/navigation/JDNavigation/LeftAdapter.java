package wanandroid.c03.jy.com.wanandroid.navigation.JDNavigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.lang.ref.SoftReference;
import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;

/**
 * User:lenovo
 * Date:2018/12/18
 * Time:19:18
 * author:lzy
 */
public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder>{

    private List<NavigationListData> mDatas;
    private OnSelectedListener mSelectedListener;
    private int mCurrentSelectedItemPosition;

    public LeftAdapter(List<NavigationListData> mDatas) {
        this.mDatas = mDatas;
    }

    public void setDatas(List<NavigationListData> datas) {
        this.mDatas = datas;
    }

    private int mOldPosition = -1;

    @NonNull
    @Override
    public LeftAdapter.LeftViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new LeftViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .navigation_laft_item,viewGroup,false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftAdapter.LeftViewHolder viewHolder, int i) {
        viewHolder.setData(mDatas.get(i));
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return (mDatas == null) ? 0 : mDatas.size();
    }

    public int getSelectedItemPostion(){
        return mCurrentSelectedItemPosition;
    }

    public void setOnSelectListener(OnSelectedListener listener){
        mSelectedListener = listener;
    }

    public void select(int targetPosition,boolean isClicked){

        mCurrentSelectedItemPosition = targetPosition;

        if(mOldPosition == targetPosition){
            return;
        }
        NavigationListData currentData = mDatas.get(targetPosition);
        currentData.setSelected(true);
        for(NavigationListData data : mDatas){
            if(data != currentData){
                data.setSelected(false);
            }
        }
        if(mOldPosition != -1){
            notifyItemChanged(mOldPosition);
        }
        if(!isClicked){
            notifyItemChanged(targetPosition);
        }

        mOldPosition = targetPosition;
    }
    public void select(int position) {
        select(position, false);
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mTitle;
        private SoftReference<LeftAdapter> mAdapterSoftReference;

        public LeftViewHolder(@NonNull final View itemView,LeftAdapter leftAdapter) {
            super(itemView);

            mAdapterSoftReference = new SoftReference<>(leftAdapter);

            mTitle = itemView.findViewById(R.id.navigation_tv_left_item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mAdapterSoftReference.get() != null){
                        mAdapterSoftReference.get().select(getAdapterPosition(),true);
                        OnSelectedListener onSelectedListener = mAdapterSoftReference.get().mSelectedListener;
                        if(onSelectedListener != null){
                            onSelectedListener.onSelect(getAdapterPosition());
                        }
                    }
                }
            });


        }

        public void setData(NavigationListData data) {
            mTitle.setText(data.getName());
            mTitle.setChecked(data.isSelected());
        }
    }


    public interface OnSelectedListener {

        void onSelect(int position);
    }

}
