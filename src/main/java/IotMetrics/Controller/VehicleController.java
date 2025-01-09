package Saracchandra.Trucker.Controller;

import java.util.List;
import Saracchandra.Trucker.entity.AlertInfo;
import Saracchandra.Trucker.entity.VehicleInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Saracchandra.Trucker.entity.VehicleTirePressure;
import Saracchandra.Trucker.entity.VehicleUpdate;
import Saracchandra.Trucker.service.VehicleService;

@CrossOrigin
@RestController
public class VehicleController {
	@Autowired
    VehicleService service;

    @GetMapping("/vehicles")
    //@RequestMapping(method = RequestMethod.GET, value = )
    public List<VehicleInfo> findAll(){
        return service.findAll();
    }
    @GetMapping("/vehicles/{id}")
    //@RequestMapping(method = RequestMethod.GET, value = )
    public VehicleInfo findByID(@PathVariable("id") String vin ){
        return service.findById(vin);
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/alerts/high")
    @GetMapping("alerts/high")
    public List<AlertInfo> findHighAlerts(){
        return service.findHighAlerts();
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/alerts/{vin}")
    @GetMapping("alerts/{vin}")
    public List<AlertInfo> findVehicleAlerts(@PathVariable("vin") String vin){
        return service.findVehicleAlerts(vin);
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/geolocation/{vin}")
    @GetMapping("/geolocation/{vin}")
    public List<VehicleUpdate> findGeoLocation(@PathVariable("vin") String vin){
        return service.findGeoLocation(vin);
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/readings",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/readings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleUpdate create(@RequestBody VehicleUpdate vehicle) {

            VehicleTirePressure pressure = new VehicleTirePressure();
            pressure.vin = vehicle.getVin();
            pressure.timestamp = vehicle.getTimestamp();
            pressure.rearRight = vehicle.tires.rearRight;
            pressure.frontRight = vehicle.tires.frontRight;
            pressure.rearLeft = vehicle.tires.rearLeft;
            pressure.frontLeft = vehicle.tires.frontLeft;
            service.create(pressure);
            service.throwAlerts(vehicle);
            return service.create(vehicle);

    }
//    @RequestMapping(method = RequestMethod.PUT, value = "/vehicles",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/vehicles",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleInfo> update(@RequestBody List<VehicleInfo> vehicles) {
        for(VehicleInfo vehicle : vehicles) {
            service.update(vehicle);
        }
        return vehicles;
    }

}
