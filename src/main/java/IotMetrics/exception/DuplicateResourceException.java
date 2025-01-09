package Saracchandra.Trucker.exception;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
public class DuplicateResourceException extends RuntimeException {
	public DuplicateResourceException(String msg){
        super(msg);
    }
}
