package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
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
   private int size = 0;

   @OneToOne(mappedBy = "parkingLot")
   private ParkingPrices parkingPrices;

   @OneToMany(mappedBy = "parkingLot" , cascade = CascadeType.ALL)
   private List<StastisticalInformation> stastisticalInformation;

   @OneToMany(mappedBy = "parkingLot")
   private List<Parking> parkings;


   public ParkingLot(String name, int rowsNum){
      this.name = name;
      this.rowsNum = rowsNum;
      this.size = 3*3*rowsNum;
      this.stastisticalInformation = new ArrayList<>();
      this.parkings = new ArrayList<>();
   }

   public void setParkingPrices(ParkingPrices parkingPrices){

      this.parkingPrices = parkingPrices;
      parkingPrices.setParkingLot(this);
   }

   public ParkingLotData getParkingLotData()
   {
      return new ParkingLotData(this.id, this.rowsNum, this.name);
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



   public List<StastisticalInformation> getStastisticalInformation() {
      return stastisticalInformation;
   }

   public void addStastisticalInformation(StastisticalInformation stastisticalInformation) {
      this.stastisticalInformation.add(stastisticalInformation);
   }

   public List<Parking> getParkings() {
      return parkings;
   }
   public void addParking(Parking parking) {
      this.parkings.add(parking);
   }

}