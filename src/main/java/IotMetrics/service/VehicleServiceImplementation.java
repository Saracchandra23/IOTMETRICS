package Saracchandra.Trucker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Saracchandra.Trucker.entity.VehicleInfo;
import Saracchandra.Trucker.entity.VehicleTirePressure;
import Saracchandra.Trucker.entity.AlertInfo;
import Saracchandra.Trucker.entity.VehicleId;
import Saracchandra.Trucker.entity.VehicleUpdate;
import Saracchandra.Trucker.exception.ResourceNotFoundException;
import Saracchandra.Trucker.repository.AlertRepository;
import Saracchandra.Trucker.repository.TireRepository;
import Saracchandra.Trucker.repository.UpdateRepository;
import Saracchandra.Trucker.repository.VehicleRepository;

@Service
public class VehicleServiceImplementation implements VehicleService {
	 @Autowired
	    VehicleRepository vehicles;

	    @Autowired
	    UpdateRepository readings;

	    @Autowired
	    TireRepository tires;

	    @Autowired
	    AlertRepository alerts;

	    @Transactional(readOnly = true)
	    public List<VehicleInfo> findAll() {
	        return (List<VehicleInfo>) vehicles.findAll();
	    }

	    @Transactional(readOnly = true)
	    public VehicleInfo findById(String ID) {
	        Optional<VehicleInfo> existing = vehicles.findById(ID);
	        if (!existing.isPresent()) {
	            throw new ResourceNotFoundException("Employee with id " + ID + " doesn't exist.");
	        }
	            return existing.get();
	    }

	    @Transactional
	    public List<VehicleUpdate> findGeoLocation(String vin) {
	        Optional<List<VehicleUpdate>> existing = readings.findGeoLocation(vin);
	        if (!existing.isPresent()){
	            throw new ResourceNotFoundException("Vehicle with vin " + vin + " doesn't exist.");
	        }
	        return existing.get();
	    }

	    @Transactional(readOnly = true)
	    public List<AlertInfo> findHighAlerts(){
	            return alerts.findHighAlerts();
	    }

	    @Transactional(readOnly = true)
	    public List<AlertInfo> findVehicleAlerts(String vin){
	        return alerts.findVehicleAlerts(vin);
	    }

	    @Transactional
	    public VehicleInfo update(VehicleInfo vehicleInfo) {
	        Optional<Saracchandra.Trucker.entity.VehicleInfo> existing = vehicles.findById(vehicleInfo.getVin());
	        if(existing.isPresent()) {
	            return null;
	        }
	        return vehicles.save(vehicleInfo);
	    }


	    @Transactional
	    public VehicleUpdate create(VehicleUpdate vehicleUpdate) {
	        Optional<VehicleInfo> existing = vehicles.findById(vehicleUpdate.getVin());
	        if(!existing.isPresent()){
	            throw new ResourceNotFoundException(" Vehicle with vin "+ vehicleUpdate.getVin() + " is not in our records");
	        }
	        return readings.save(vehicleUpdate);
	    }

	    @Transactional
	    public VehicleTirePressure create(VehicleTirePressure pressure) {
	        Optional<VehicleInfo> existing = vehicles.findById(pressure.getVin());
	        if(!existing.isPresent()){
	            throw new ResourceNotFoundException(" Vehicle with vin "+ pressure.getVin() + " is not in our records");
	        }
	        return tires.save(pressure);
	    }


	    public void throwAlerts(VehicleUpdate vehicle){
	        Optional<VehicleInfo> vehicleInfo = vehicles.findById(vehicle.getVin());
	        Optional<VehicleTirePressure> tirePressure = tires.findById(new VehicleId(vehicle.getVin(),vehicle.getTimestamp()));

	        if(vehicleInfo.isPresent()){
	            if(vehicleInfo.get().getRedlineRpm() < vehicle.getEngineRpm())
	            {
	                AlertInfo alertInfo = new AlertInfo(vehicle.getVin(),vehicle.getTimestamp());
	                alertInfo.setPriority("HIGH");
	                alertInfo.setAlertType("RPM");
	                alertInfo.setMessage(" Vehicle moving at high RPM");
	                alerts.save(alertInfo);
	            }
	            if(vehicleInfo.get().getMaxFuelVolume()*0.1 > vehicle.getFuelVolume()){
	                AlertInfo alertInfo = new AlertInfo(vehicle.getVin(),vehicle.getTimestamp());
	                alertInfo.setAlertType("FUEL");
	                alertInfo.setPriority("MEDIUM");
	                alertInfo.setMessage(" LOW VOLUME ALERT");
	                alerts.save(alertInfo);
	            }

	            if(tirePressure.get().getRearLeft() < 32 || tirePressure.get().getRearLeft() > 36 || tirePressure.get().getRearRight() < 32 || tirePressure.get().getRearRight() > 36  ||
	                    tirePressure.get().getFrontLeft() < 32 || tirePressure.get().getFrontLeft() > 36 || tirePressure.get().getFrontRight() < 32 || tirePressure.get().getFrontRight() > 36 )
	            {
	                AlertInfo alertInfo = new AlertInfo(vehicle.getVin(),vehicle.getTimestamp());
	                alertInfo.setPriority("LOW");
	                alertInfo.setAlertType("TIRE");
	                alertInfo.setMessage(" PLEASE CHECK TIRE PRESSURE");
	                alerts.save(alertInfo);
	            }

	            if(vehicle.isEngineCoolantLow() || vehicle.isCheckEngineLightOn()) {
	                AlertInfo alertInfo = new AlertInfo(vehicle.getVin(),vehicle.getTimestamp());
	                alertInfo.setPriority("LOW");
	                alertInfo.setAlertType("ENGINE");
	                alertInfo.setMessage(" PLEASE CHECK ENGINE-COOLANT and ENGINE CONDITION");
	                alerts.save(alertInfo);
	            }
	        }

	    }


}
