package Saracchandra.Trucker.repository;

import org.springframework.data.repository.CrudRepository;
import Saracchandra.Trucker.entity.VehicleId;
import Saracchandra.Trucker.entity.VehicleTirePressure;

public interface TireRepository extends CrudRepository<VehicleTirePressure, VehicleId> {

}
