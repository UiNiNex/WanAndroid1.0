package wanandroid.c03.jy.com.wanandroid.adepter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;

/**
 * User:lenovo
 * Date:2018/12/12
 * Time:11:03
 * author:lzy
 */
public class TwoKHRecyclerAdpter extends IBasRecyclerAdepter<TwoKHRecyclerAdpter.ViewHolder> {

    private List<TwoKonwledgehierarchypage.DatasBean> list;

    public TwoKHRecyclerAdpter(List<TwoKonwledgehierarchypage.DatasBean> list) {
        this.list = list;
    }

    public TwoKHRecyclerAdpter() {
        super();
    }

    public void setList(List<TwoKonwledgehierarchypage.DatasBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_knowhierar_carview,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        viewHolder1.item_kh_author.setText(list.get(i).getAuthor());
        viewHolder1.item_kh_data.setText(list.get(i).getDesc());
        viewHolder1.item_kh_title.setText(list.get(i).getTitle());
        viewHolder1.item_kh_type.setText(list.get(i).getSuperChapterName());
        viewHolder1.item_kh_data.setText(list.get(i).getNiceDate());
        viewHolder1.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTwoKHOnClike != null){
                    mTwoKHOnClike.twoKHOnClike(list.get(i));
                }
            }
        });
        if(mOpenAnimationEnable){

            addAnimation(viewHolder1);
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView item_kh_author;
        public TextView item_kh_title;
        public TextView item_kh_data;
        public TextView item_kh_type;
        public CheckBox item_kh_collect;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.item_kh_author = (TextView) rootView.findViewById(R.id.item_kh_author);
            this.item_kh_title = (TextView) rootView.findViewById(R.id.item_kh_title);
            this.item_kh_data = (TextView) rootView.findViewById(R.id.item_kh_data);
            this.item_kh_type = (TextView) rootView.findViewById(R.id.item_kh_type);
            this.item_kh_collect = (CheckBox) rootView.findViewById(R.id.item_kh_collect);

        }
    }

    private TwoKHOnClike mTwoKHOnClike;

    public void setmTwoKHOnClike(TwoKHOnClike mTwoKHOnClike) {
        this.mTwoKHOnClike = mTwoKHOnClike;
    }

    public interface TwoKHOnClike{
        void twoKHOnClike(TwoKonwledgehierarchypage.DatasBean datasBean);
    }

}
