package fp.space_test;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import fp.space.*;

public class ASpace {
	private static SpaceShip example = new SpaceShip("SpaceX", "X23| Burdeos| Sevilla| España", "Somos la mejor lanzando cohetes",
			LocalDateTime.of(2021, 7, 21, 14, 32), 15.5F,
			"Failure", Rocket.CRASH, 7, Duration.ofDays(10));
	private static void get() {
		System.out.println("Compañía: " + example.getCompany());
		System.out.println("Ubi: " + example.getControlCenter());
		System.out.println("Pais: " + example.getCountry());
		System.out.println("Detalles: " + example.getDetails());
		System.out.println("Fecha Salida: " + example.getDatum());
		System.out.println("Precio: " + example.getPrize());
		System.out.println("Status Mission: " + example.getStatus());
		System.out.println("Status cohete: " + example.getRstatus());
		System.out.println("Duracion: " + example.getLengthOfExpedition());
		System.out.println("Fecha retorno: " + example.getReturnDate());
	}
	private static void set() {
		example.setDetails("Ahora no somos los mejores");
		example.setPrize(25.05F);
		example.setStatus("Partial Failure");
		example.setRstatus(Rocket.LANDED);
		example.setReturnDate(LocalDateTime.now().plus(Duration.ofDays(30)));
	}
	private static void test5(SpaceShips ss) {
		System.out.println("Diccionario con los paises por compañia");
		for(Entry<String, List<String>> i: ss.getMapofCountryByCompany().entrySet()) 
			System.out.println(i);	
		System.out.println("\n");
	}
	private static void test1(SpaceShips ss) {
		System.out.println("Pintar los cohetes por el tipo, en función de la fecha de despegue invertida");
		for(SpaceShip i: ss.filterByRockerDatumI(Rocket.CRASH)) 
			System.out.println(i);	
		System.out.println("\n");
	}
	private static void test2(SpaceShips ss) {
		float temp = 500000F;
		System.out.println("Existe algún cohete con un precio de " + temp + "?");
		if (ss.anySpaceShipWithLessPrize(temp)) {
			System.out.println("Existe algún cohete con una financiación inferior a la indicada");
		}else {
			System.out.println("No existe alguno");
		}
		System.out.println("\n");	
	}
	private static void test3(SpaceShips ss) {
		if(SpaceShips.anySpaceShipWithATypeOfStatus(ss)) {
			System.out.println("Todos los cohetes han sido satisfactorios");
		}else {
			System.out.println("No Todos los cohetes han sido satisfactorios");
		}
		System.out.println("\n");
	}
	private static void test4(SpaceShips ss) {
		System.out.println("Duracion media entre las fechas definidas?");
		System.out.println(ss.averageDurationPatched(LocalDateTime.now().minusYears(20), LocalDateTime.now().minusYears(30),4));
		System.out.println("\n");
	}
	private static void test6(SpaceShips ss) {
		System.out.println("La nave espacial que tardó más es: ");
		try {
			System.out.println(ss.getLongestDurationSpaceShip());
		}catch(NoSuchElementException nsee) {
			System.err.println("No hay ninguno");
		}
	}
	private static void test7(SpaceShips ss) {
		for(SpaceShip i: ss.changeStatusByLongestDurationSpaceShipSortedByStatus()) {
				System.out.println(i.getRstatus());
		}
	}
	private static void test8(SpaceShips ss) {
		try {
		List<SpaceShip> temp = (List<SpaceShip>) ss.getCheapestAndExpensiveRocket();
		System.out.println("La más cara es: " + temp.get(0));
		System.out.println("La más barata es: " + temp.get(1));
		}catch(NoSuchElementException nsee) {
			System.err.println("No se puede extraer la pareja");
		}
		
	}
	private static void test9(SpaceShips ss) {
		for(Entry<Rocket, Integer> i: ss.getMapNumberOfEachRocketStatus().entrySet())
			System.out.println(i);
	}
	private static void test10(SpaceShips ss) {
		for(Entry<LocalDateTime, Map<Rocket, TreeSet<SpaceShip>>> i: ss.getMapSpaceShipsByTypesOfRocketByDate().entrySet())
			System.out.println(i.getKey());
	}
	private static void test11(SpaceShips ss) {
		for(Entry<String, List<String>> i: ss.getCompanyByControlCenter().entrySet())
			System.out.println(i.getKey());
	}
	public static void main(String[] args) {
		List<SpaceShip> data = null;
		//###################################################//
		
//		System.out.println("Ejemplo datos get:");
//		get();
//		//###################################################//
//		System.out.println("\n\nEjemplo datos set:");
//		set();
//		//###################################################//
//		get();
		
		
		//###################FICHERO LEIDO###################//
		
		try {
			data = FactoriaSpaceShips.getLines(".\\data\\Space_Corrected.csv");
		}catch(IOException ioe) {}
//		System.out.println(data.size()); // 4 mil y pico, no son todas, pero la diferencia es nimia, y se debe a condiciones que no puedo controlar globalmente
		SpaceShips ss = new SpaceShips(data);
//		test1(ss);
//		test2(ss);
//		test3(ss);
//		test4(ss);
//		test5(ss);
//		test6(ss);
//		test7(ss);
//		test8(ss);
//		test9(ss);
//		test10(ss);
		test11(ss);
//		####################################################//
		
	}

}
