package fp.space;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class FactoriaSpaceShips {
	public static List<String> readFile(String dir) throws IOException{
		return Files.readAllLines(Paths.get(dir));
	}
	public static List<SpaceShip> getLines(String dir) throws IOException{
		List<SpaceShip> items = new LinkedList<SpaceShip>();		
		for(String line : FactoriaSpaceShips.readFile(dir)) {
			try {
				items.add(FactoriaSpaceShips.parseSpaceShip(line));
			}catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			}catch(DateTimeParseException dpe) {
				System.err.println(dpe.getMessage());
			}
		}
		return items;
	}
	
	private static SpaceShip parseSpaceShip(String data) throws DateTimeParseException{
			String[] temp = data.split(";");
			if (temp.length != 9) throw new IllegalArgumentException("Hay un error de formato en la linea ");
			String company = temp[0], details = temp[3], status = temp[6], rstatus = temp[4], controlCenter = temp[1];
			LocalDateTime datum = LocalDateTime.parse(temp[2].trim(), DateTimeFormatter.ofPattern("EEE MMM dd| yyyy HH:mm 'UTC'").withLocale(Locale.ENGLISH));
			float prize = Float.parseFloat(temp[5].trim().replaceAll(",","."));
			Duration lengthOfExpedition = Duration.ofDays(Long.parseLong(temp[8].trim()));
			int crew = Integer.parseInt(temp[7].trim());
			return new SpaceShip(company,controlCenter,details,datum,prize,status,Rocket.valueOf(rstatus.toUpperCase()),crew,lengthOfExpedition);
	}
}
