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
   private int size;


   public ParkingLot(int parkingLotId, int size){
      this.parkingLotId = parkingLotId;
      this.size = size;
   }
   public ParkingLot(ParkingLotId parkingLotId){
      this.parkingLotId = parkingLotId.getParkingLotId();
      this.size = parkingLotId.getSize()
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

   public void changePrice(int id, ParkingPrices newPrice)
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
