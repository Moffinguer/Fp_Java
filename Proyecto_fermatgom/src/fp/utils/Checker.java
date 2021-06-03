package fp.utils;

import java.time.Duration;

public class Checker {
	public static String trimer(String data){
		return data.trim();
	}
	
	public static void notNull(Object data) {
		if (data == null) throw new NullPointerException("No puede haber un argumento nulo");
	}
	
	public static void notByte(Integer data, String message) {
		if (data > 127 || data < -128) throw new IllegalArgumentException(message);
	}
	
	public static void isNegativeOrZero(Integer data, String message) {
		if (data < 1) throw new IllegalArgumentException(message);	
	}
	
	public static void isEmptyString(String data, String message) {
		if (data.equals("")) throw new IllegalArgumentException(message);
	}
	
	public static void isNegativeOrZero(Double data, String message) {
		if (data < 1) throw new IllegalArgumentException(message);	
	}
	
	public static void isNegativeOrZero(Duration data, String message) {
		if (data.isZero() || data.isNegative()) throw new IllegalArgumentException(message);	
	}
	
	public static void notAnStatus(String message) {
		throw new IllegalArgumentException(message);
	}
	
	public static void checkLocations(String[] args) {
		if (args.length > 4 || args.length < 3) throw new IllegalArgumentException("No hay suficientes datos para la ubicación");
	}
}
