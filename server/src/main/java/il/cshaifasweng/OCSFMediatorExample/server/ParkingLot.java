package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ParkingLot")
public class ParkingLot {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   public String name;
   public int price;
   public String description;
   public String imageUrl;
   public int priceAfterDiscount;


   public ParkingLot(String name, int price, String description, String imageUrl) {
      this.name=name;
      this.price=price;
      this.description=description;
      this.imageUrl=imageUrl;
      this.priceAfterDiscount=price;
   }
   public ParkingLot(ParkingLotData ParkingLotData){
      name = ParkingLotData.getName();
      price = ParkingLotData.getPrice();
      description = ParkingLotData.getDescription();
      imageUrl = ParkingLotData.getImageURL();
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

   public ParkingLotData GetItemData() {
      return new ParkingLotData(id, name, price, description, imageUrl, priceAfterDiscount);
   }

   public int getPriceAfterDiscount() {
      return priceAfterDiscount;
   }

   public void setPriceAfterDiscount(int priceAfterDiscount) {
      this.priceAfterDiscount = priceAfterDiscount;
   }

   public ParkingLot(int id, String name, int price, String description, String imageUrl, int priceAfterDiscount) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.description = description;
      this.imageUrl = imageUrl;
      this.priceAfterDiscount = priceAfterDiscount;
   }

   public void changePrice(int id,int newPrice)
   {
      App.SafeStartTransaction();
      ParkingLot temp=new ParkingLot();
      for (ParkingLot item :items)
      {
         if(item.getId()==id)
         {
            temp=item;
         }
      }
      temp.setPrice(newPrice);
      App.session.save(temp);
      App.session.flush();
      App.SafeCommit();
   }
}
