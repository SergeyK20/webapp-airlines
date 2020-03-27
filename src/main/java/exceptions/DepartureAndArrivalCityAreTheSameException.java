package exceptions;

public class DepartureAndArrivalCityAreTheSameException extends Exception {
    public DepartureAndArrivalCityAreTheSameException(){
        super("Cities of arrival and departure must not match...");
    }
}
