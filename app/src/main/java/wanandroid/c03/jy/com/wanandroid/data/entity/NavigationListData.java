package wanandroid.c03.jy.com.wanandroid.data.entity;

import java.util.List;


/**
 * @author quchao
 * @date 2018/2/24
 */

public class NavigationListData {

    /**
     * "articles": [],
     * "cid": 272,
     * "name": "常用网站"
     */

    private List<Article> articles;
    private int cid;
    private String name;
    private boolean isSelected;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
