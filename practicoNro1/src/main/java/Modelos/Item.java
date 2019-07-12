package Modelos;

public class Item {

    String id;
    String site_id;
    String title;
    double price;
    String listing_type_id;
    String stop_time;
    String thumbnail;
    String tags[];
    Currency currency;


    public Item(){

    }

    public Item(String id, String site_id, String title, double price, String listing_type_id, String stop_time, String thumbnail, String[] tags, Currency currency) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.listing_type_id = listing_type_id;
        this.stop_time = stop_time;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.currency = currency;
    }
    public Item(ItemResultado itemResultado) {
        this.id = itemResultado.getId();
        this.site_id = itemResultado.getSite_id();
        this.title = itemResultado.getTitle();
        this.price = itemResultado.getPrice();
        this.listing_type_id = itemResultado.getListing_type_id();
        this.stop_time = itemResultado.getStop_time();
        this.thumbnail = itemResultado.getThumbnail();
        this.tags = itemResultado.getTags();
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}


