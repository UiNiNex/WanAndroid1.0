package wanandroid.c03.jy.com.wanandroid.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * User:lenovo
 * Date:2019/1/13
 * Time:19:12
 * author:lzy
 */

@Entity
public class CollectBean {

    @Id
    private long id;
    private int userId;
    private String homeItemIsNew;
    private String homeItemTag;
    private String homeIteAuthor;
    private String homeItemTime;
    private String homeItemPic;
    private String homeItemTitle;
    private String homeItemChapterName;

    @Generated(hash = 631579231)
    public CollectBean(long id, int userId, String homeItemIsNew,
            String homeItemTag, String homeIteAuthor, String homeItemTime,
            String homeItemPic, String homeItemTitle, String homeItemChapterName) {
        this.id = id;
        this.userId = userId;
        this.homeItemIsNew = homeItemIsNew;
        this.homeItemTag = homeItemTag;
        this.homeIteAuthor = homeIteAuthor;
        this.homeItemTime = homeItemTime;
        this.homeItemPic = homeItemPic;
        this.homeItemTitle = homeItemTitle;
        this.homeItemChapterName = homeItemChapterName;
    }

    @Generated(hash = 420494524)
    public CollectBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getHomeItemIsNew() {
        return this.homeItemIsNew;
    }
    public void setHomeItemIsNew(String homeItemIsNew) {
        this.homeItemIsNew = homeItemIsNew;
    }
    public String getHomeItemTag() {
        return this.homeItemTag;
    }
    public void setHomeItemTag(String homeItemTag) {
        this.homeItemTag = homeItemTag;
    }
    public String getHomeIteAuthor() {
        return this.homeIteAuthor;
    }
    public void setHomeIteAuthor(String homeIteAuthor) {
        this.homeIteAuthor = homeIteAuthor;
    }
    public String getHomeItemTime() {
        return this.homeItemTime;
    }
    public void setHomeItemTime(String homeItemTime) {
        this.homeItemTime = homeItemTime;
    }
    public String getHomeItemPic() {
        return this.homeItemPic;
    }
    public void setHomeItemPic(String homeItemPic) {
        this.homeItemPic = homeItemPic;
    }
    public String getHomeItemTitle() {
        return this.homeItemTitle;
    }
    public void setHomeItemTitle(String homeItemTitle) {
        this.homeItemTitle = homeItemTitle;
    }
    public String getHomeItemChapterName() {
        return this.homeItemChapterName;
    }
    public void setHomeItemChapterName(String homeItemChapterName) {
        this.homeItemChapterName = homeItemChapterName;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
