package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotId;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPrices;

@Entity
@Table(name = "ParkingLot")
public class ParkingLot {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int parkingLotId;
   private String name;
   private int size;
   @Column(name = "regular_price")
   private double parkingPrice;
   @Column(name = "ordered_price")
   private double orderedParkingPrice;
   @Column(name = "regular_sub_price")
   private double regularSubscriptionPrice;
   @Column(name = "multi_sub_price")
   private double regularSubscriptionMultiCarsPrice;
   @Column(name = "fully_sub_price")
   private double fullySubscriptionPrice;


   public ParkingLot(String name, int size){
      this.name = name;
      this.size = size;
      this.parkingPrice = 0;
      this.orderedParkingPrice = 0;
      this.regularSubscriptionPrice = 0;
      this.regularSubscriptionMultiCarsPrice = 0;
      this.fullySubscriptionPrice = 0;
   }
   public ParkingLot(ParkingLotId parkingLotId){
      this.name = parkingLotId.getName();
      this.size = parkingLotId.getSize();
      this.parkingPrice = parkingLotId.getPrices().getParkingPrice();
      this.orderedParkingPrice =  parkingLotId.getPrices().getOrderedParkingPrice();
      this.regularSubscriptionPrice =  parkingLotId.getPrices().getRegularSubscriptionPrice();
      this.regularSubscriptionMultiCarsPrice =  parkingLotId.getPrices().getRegularSubscriptionMultiCarsPrice();
      this.fullySubscriptionPrice =  parkingLotId.getPrices().getFullySubscriptionPrice();
   }
   public ParkingLot(){
      this.size = 0;
      this.parkingPrice = 0;
      this.orderedParkingPrice = 0;
      this.regularSubscriptionPrice = 0;
      this.regularSubscriptionMultiCarsPrice = 0;
      this.fullySubscriptionPrice = 0;
   }
   public int getParkingLotId(){
      return this.parkingLotId;
   }

   public void setSize(int size){
      this.size = size;
   }
   public int getSize(){
      return this.size;
   }
   public void setParkingPrice(double parkingPrice){
      this.parkingPrice = parkingPrice;
   }
   public double getParkingPrice(){
      return this.parkingPrice;
   }
   public void setOrderedParkingPrice(double orderedParkingPrice){
      this.orderedParkingPrice = orderedParkingPrice;
   }
   public double getOrderedParkingPrice(){
      return this.orderedParkingPrice;
   }
   public void setRegularSubscriptionPrice(double regularSubscriptionPrice){
      this.regularSubscriptionPrice = regularSubscriptionPrice;
   }
   public double getRegularSubscriptionPrice(){
      return this.regularSubscriptionPrice;
   }
   public void setRegularSubscriptionMultiCarsPrice(double regularSubscriptionMultiCarsPrice){
      this.regularSubscriptionMultiCarsPrice = regularSubscriptionMultiCarsPrice;
   }
   public double getRegularSubscriptionMultiCarsPrice(){
      return this.parkingPrice;
   }
   public void setFullySubscriptionPrice(double fullySubscriptionPrice){
      this.fullySubscriptionPrice = fullySubscriptionPrice;
   }
   public double getFullySubscriptionPrice(){
      return this.fullySubscriptionPrice;
   }

}
