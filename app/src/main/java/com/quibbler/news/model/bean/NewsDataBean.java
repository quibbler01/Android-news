package com.quibbler.news.model.bean;

/**
 * News Request Data
 *
 * <p>example<p>
 * uniquekey : 0354d936d305878011b2f67dfbd469b5
 * title : 曝光台|268万买的宾利质保期内发生损坏，4S店补偿方案：下次保养时打折
 * date : 2020-09-26 12:34
 * category : 社会
 * author_name : 扬子晚报网
 * url : https://mini.eastday.com/mobile/200926123418544.html
 * thumbnail_pic_s : https://01imgmini.eastday.com/mobile/20200926/20200926123418_d12e21d32de86186c22677af2888dc98_2_mwpm_03200403.jpg
 * thumbnail_pic_s02 : http://01imgmini.eastday.com/mobile/20200926/20200926123418_d12e21d32de86186c22677af2888dc98_1_mwpm_03200403.jpg
 * thumbnail_pic_s03 : http://02imgmini.eastday.com/mobile/20200926/2020092612_f27f1e290bcb4353ae68ebbf7c844aac_4038_mwpm_03200403.jpg
 */
public class NewsDataBean {
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }
}
