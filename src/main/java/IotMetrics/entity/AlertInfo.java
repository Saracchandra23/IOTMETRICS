package Saracchandra.Trucker.entity;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table
@IdClass(AlertId.class)
public class AlertInfo implements Serializable{
	@Id
    String vin;
    @Id
    String timestamp;
    @Id
    String alertType;
    String Priority;
    String message;

    public AlertInfo(){}

    public AlertInfo(String vin, String timestamp) {
        this.vin = vin;
        this.timestamp = timestamp;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getVin() {
        return vin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
