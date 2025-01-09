package Saracchandra.Trucker.service;

import Saracchandra.Trucker.entity.VehicleInfo;
import Saracchandra.Trucker.entity.VehicleTirePressure;
import Saracchandra.Trucker.entity.VehicleUpdate;
import Saracchandra.Trucker.entity.AlertInfo;

import java.util.List;


public interface VehicleService {
	 VehicleInfo update(VehicleInfo vehicle);
	    List<VehicleInfo> findAll();
	    VehicleInfo findById(String ID);
	    VehicleUpdate create(VehicleUpdate vehicle);
	    VehicleTirePressure create(VehicleTirePressure pressure);
	    List<AlertInfo> findHighAlerts();
	    List<VehicleUpdate> findGeoLocation(String vin);
	    List<AlertInfo> findVehicleAlerts(String vin);

	    void throwAlerts(VehicleUpdate vehicle);
	}

