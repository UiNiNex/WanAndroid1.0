package wanandroid.c03.jy.com.wanandroid.adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.GlideApp;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;

/**
 * User:lenovo
 * Date:2018/12/13
 * Time:10:53
 * author:lzy
 */
public class ProjectRecyclerAdepter extends IBasRecyclerAdepter<ProjectRecyclerAdepter.ViewHolder> {

    private List<ProjectAccountsArtileData.DatasBean> list;
    private Context mContext;

    public ProjectRecyclerAdepter(List<ProjectAccountsArtileData.DatasBean> list,Context context) {
        this.list = list;
        mContext = context;
    }

    public void setList(List<ProjectAccountsArtileData.DatasBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_project_artile_rec,
                viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(inflate);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder viewHolder1 = (MyViewHolder) viewHolder;

        GlideApp.with(mContext).load(list.get(i).getEnvelopePic()).placeholder(R.drawable
                .ic_placeholder_bg_default).into(viewHolder1.item_projc_image);
        viewHolder1.item_project_date.setText(list.get(i).getNiceDate()+"\u3000"+list.get(i).getAuthor());
        viewHolder1.item_project_title.setText(list.get(i).getTitle());
        viewHolder1.textView2.setText(list.get(i).getDesc());

        viewHolder1.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mProjectOnclick!= null){
                    AutoMassage autoMassage = new AutoMassage();
                    autoMassage.url = list.get(i).getLink();;
                    autoMassage.title = list.get(i).getChapterName();
                    mProjectOnclick.onClick(autoMassage);
                }
            }
        });

        if(mOpenAnimationEnable){
            addAnimation(viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public ImageView item_projc_image;
        public TextView item_project_title;
        public TextView textView2;
        public TextView item_project_date;

        public MyViewHolder(@NonNull View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.item_projc_image = (ImageView) rootView.findViewById(R.id.item_projc_image);
            this.item_project_title = (TextView) rootView.findViewById(R.id.item_project_title);
            this.textView2 = (TextView) rootView.findViewById(R.id.item_project_sourrcess);
            this.item_project_date = (TextView) rootView.findViewById(R.id.item_project_date);
        }
    }

    public class ViewHolder {
    }

    private ProjectOnclick mProjectOnclick;

    public void setmProjectOnclick(ProjectOnclick mProjectOnclick) {
        this.mProjectOnclick = mProjectOnclick;
    }

    public interface ProjectOnclick{
        void onClick(AutoMassage autoMassage);
    }
}
