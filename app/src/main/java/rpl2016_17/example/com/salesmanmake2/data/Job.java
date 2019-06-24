package rpl2016_17.example.com.salesmanmake2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {
    private int id;
    private String description;
    private String shop_name;
    private String shop_address;
    private String shop_phone;

    public Job() {
        this.id = id;
        this.description = description;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_phone = shop_phone;
    }


    protected Job(Parcel in) {
        id = in.readInt();
        description = in.readString();
        shop_name = in.readString();
        shop_address = in.readString();
        shop_phone = in.readString();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

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

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(shop_name);
        dest.writeString(shop_address);
        dest.writeString(shop_phone);
    }
}
