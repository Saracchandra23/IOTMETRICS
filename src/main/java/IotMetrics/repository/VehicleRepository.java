package Saracchandra.Trucker.repository;
import Saracchandra.Trucker.entity.VehicleInfo;
import org.springframework.data.repository.CrudRepository;
public interface VehicleRepository extends CrudRepository<VehicleInfo, String> {

}
