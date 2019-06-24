package rpl2016_17.example.com.salesmanmake2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {
    private int id;
    private String description;
    private String shop_name;
    private String status;
    private String location;
    private String created_at;
    private String proof_image;
    private String shop_address;
    private String shop_phone;

    public Job() {
        this.id = id;
        this.location = location;
        this.proof_image = proof_image;
        this.status = status;
        this.created_at = created_at;
        this.description = description;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_phone = shop_phone;
    }


    protected Job(Parcel in) {
        id = in.readInt();
        location = in.readString();
        proof_image = in.readString();
        status = in.readString();
        created_at = in.readString();
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProof_image() {
        return proof_image;
    }

    public void setProof_image(String proof_image) {
        this.proof_image = proof_image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(location);
        dest.writeString(status);
        dest.writeString(created_at);
        dest.writeString(description);
        dest.writeString(shop_name);
        dest.writeString(proof_image);
        dest.writeString(shop_address);
        dest.writeString(shop_phone);
    }
}
