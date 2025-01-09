package Saracchandra.Trucker.entity;

import java.io.Serializable;
import java.util.Objects;

public class AlertId implements Serializable{
	String vin;
    String timestamp;
    String alertType;

    public AlertId(){}

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public AlertId(String vin, String timestamp, String alertType) {
        this.vin = vin;
        this.timestamp = timestamp;
        this.alertType = alertType;
    }



    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertId that = (AlertId) o;
        return vin.equals(that.vin) &&
                timestamp.equals(that.timestamp) && alertType.equals(that.alertType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, timestamp, alertType);
    }
}
