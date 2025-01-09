package Saracchandra.Trucker.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import Saracchandra.Trucker.entity.VehicleId;
import Saracchandra.Trucker.entity.VehicleUpdate;
public interface UpdateRepository extends CrudRepository<VehicleUpdate, VehicleId> {
	@Query(value = " select * from vehicle_update v where v.vin = :vin and TIMESTAMPDIFF(minute, current_timestamp, v.timestamp) < 30 ", nativeQuery = true)
    Optional<List<VehicleUpdate>> findGeoLocation(@Param("vin") String vin);
}

