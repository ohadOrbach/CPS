package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;

@Entity
@Table(name = "PriceTable")
public class PriceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public String name;
    public int price;
    public String description;
    public String imageUrl;

    public int priceAfterDiscount;


    public PriceTable(String name, int price, String description, String imageUrl) {
        this.name=name;
        this.price=price;
        this.description=description;
        this.imageUrl=imageUrl;
        this.priceAfterDiscount=price;
    }
    public PriceTable(PriceTableData PriceTableData){
        name = PriceTableData.getName();
        price = PriceTableData.getPrice();
        description = PriceTableData.getDescription();
        imageUrl = PriceTableData.getImageURL();
        this.priceAfterDiscount = price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String toString(){//overriding the toString() method
        return "id: "+id+", name: "+name+",\ndescription: "+description;
    }

    public PriceTableData GetItemData() {
        return new PriceTableData(id, name, price, description, imageUrl, priceAfterDiscount);
    }

    public int getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(int priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public PriceTable(int id, String name, int price, String description, String imageUrl, int priceAfterDiscount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.priceAfterDiscount = priceAfterDiscount;
    }

}
