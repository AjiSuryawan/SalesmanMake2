package rpl2016_17.example.com.salesmanmake2;

public class Item {
    private int id;
    private String description;
    private String shop_name;
    private String shop_address;
    private int shop_phone;

    public Item() {
        this.id = id;
        this.description = description;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_phone = shop_phone;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public int getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(int shop_phone) {
        this.shop_phone = shop_phone;
    }
}
