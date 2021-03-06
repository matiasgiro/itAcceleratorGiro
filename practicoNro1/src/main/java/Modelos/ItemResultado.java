package Modelos;

import java.util.Date;

public class ItemResultado {

    String id;
    String site_id;
    String title;
    double price;
    String listing_type_id;
    String stop_time;
    String thumbnail;
    String tags[];
    String currency_id;


    public ItemResultado(){

    }

    public ItemResultado(String id, String site_id, String title, double price, String listing_type_id, String stop_time, String thumbnail, String[] tags, String currency_id) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.listing_type_id = listing_type_id;
        this.stop_time = stop_time;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.currency_id = currency_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listing_type_id) {
        this.listing_type_id = listing_type_id;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }
}


