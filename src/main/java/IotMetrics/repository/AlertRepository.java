package Saracchandra.Trucker.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import Saracchandra.Trucker.entity.AlertInfo;
import Saracchandra.Trucker.entity.AlertId;
import java.util.List;

public interface AlertRepository extends CrudRepository<AlertInfo, AlertId> {
	@Query(value = "SELECT * FROM alert_info where Priority = 'HIGH' order by timestamp DESC ",nativeQuery = true)
    List<AlertInfo> findHighAlerts();

    @Query(value = "SELECT * FROM alert_info where vin =:vin ",nativeQuery = true)
    List<AlertInfo> findVehicleAlerts(@Param("vin") String vin);
}

