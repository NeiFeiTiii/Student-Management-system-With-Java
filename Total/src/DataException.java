//日期的异常
import java.io.Serializable;

public class DataException extends Exception implements Serializable {
    private String message;
    public DataException(String msg){
        super(msg);
        message = msg;
    }
}

