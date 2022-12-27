package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;

import java.util.Objects;

@Entity
@Table(name = "parkingLot")
public class ParkingLot {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String name;
   private int rowsNum = 0;
   private int rowSize = 0;
   private int size = 0;

   @OneToOne(mappedBy = "parkingLot")
   private ParkingPrices parkingPrices;

   public ParkingLot(String name, int rowsNum, int rowSize){
      this.name = name;
      this.rowsNum = rowsNum;
      this.rowSize = rowSize;
      this.size = rowSize*rowsNum;
   }

   public void setParkingPrices(ParkingPrices parkingPrices){

      this.parkingPrices = parkingPrices;
      parkingPrices.setParkingLot(this);
   }

//   public ParkingLot(ParkingLotData parkingLotData){
//      this.row = parkingLotData.getName();
//      this.size = parkingLotData.getSize();
//      this.parkingPrices = parkingLot.getPrices();
//   }

   public ParkingLot() {}

   public int getParkingLotId(){
      return this.id;
   }

   public void setSize(int size){
      this.size = size;
   }
   public int getSize(){
      return this.size;
   }
   public void setParkingPrice(double newPrice, String type)
   {
      if(Objects.equals(type, "parking"))
      {
         parkingPrices.setParkingPrice(newPrice);
      }
      else if(Objects.equals(type, "ordered"))
      {
         parkingPrices.setOrderedParkingPrice(newPrice);
      }
      else if(Objects.equals(type, "regularSubscription"))
      {
         parkingPrices.setRegularSub(newPrice);
      }
      else if(Objects.equals(type, "regularSubscriptionMultiCars"))
      {
         parkingPrices.setRegularSubMulti(newPrice);
      }
      else
      {
         parkingPrices.setFullSubPrice(newPrice);
      }
   }
   public String getName() { return this.name; }

   public ParkingPrices getAllPrices() {
      return this.parkingPrices;
   }

   public int getRows() { return this.rowsNum; }

   public ParkingPricesData getAllPricesData() {
      return new ParkingPricesData(parkingPrices.getParkingLotId(), parkingPrices.getParkingPrice()
      , parkingPrices.getOrderedParkingPrice());
   }
}
