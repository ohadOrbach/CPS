package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;

import java.util.Collection;
import java.util.List;
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

   public ParkingLotData getParkingLotData()
   {
      return new ParkingLotData(this.id, this.rowsNum, this.size, this.name);
   }


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
      if(Objects.equals(type, "Casual"))
      {
         parkingPrices.setParkingPrice(newPrice);
      }
      else if(Objects.equals(type, "Ordered")) {
         parkingPrices.setOrderedParkingPrice(newPrice);
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

   @OneToMany(mappedBy = "parkingLot")
   private Collection<ParkingOrder> parkingLot;

   public Collection<ParkingOrder> getParkingLot() {
      return parkingLot;
   }

   public void setParkingLot(Collection<ParkingOrder> parkingLot) {
      this.parkingLot = parkingLot;
   }
}