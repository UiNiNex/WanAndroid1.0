package wanandroid.c03.jy.com.wanandroid.data.entity;

import java.util.List;

/*
 * created by taofu on 2018/12/5
 **/
public class ArticleData {

    private List<HomeUser> datas;
    private int curPage ;


    public List<HomeUser> getDatas() {
        return datas;
    }

    public void setDatas(List<HomeUser> datas) {
        this.datas = datas;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
