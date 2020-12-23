package exceptions;

// this is the exception for the invalid stock entries
public class StockCannotBeEditedException extends Exception {
    public StockCannotBeEditedException(String message) {
        super(message);
    }
}
