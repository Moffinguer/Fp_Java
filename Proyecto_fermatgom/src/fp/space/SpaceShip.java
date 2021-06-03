package fp.space;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Locale;

import fp.utils.Checker;

public class SpaceShip implements Comparable<SpaceShip> {
	private String company, controlCenter, country, details;
	private LocalDateTime datum;
	private Float prize;
	private Boolean status;
	private Rocket rstatus;
	private Byte crew;
	private Duration lengthOfExpedition;

	private void checkNulls(Object... args) {
		for (Object item : args) {
			Checker.notNull(item);
			Checker.isEmptyString(item.toString().trim(), "Algún parametro, no está lleno");
		}

	}

	private Boolean status(String data) {
		Boolean res = null;
		switch (data) {
		case "Success":
			res = true;
			break;
		case "Failure":
			res = false;
			break;
		case "Partial Failure": case "Prelaunch Failure":
			break;
		default:
			Checker.notAnStatus("Ese status no es conocido");
		}
		return res;
	}

	private String lastDestiny(String[] destiny) {
		String res = "";
		for (int i = 2; i < destiny.length; i++) {
			Checker.isEmptyString(destiny[i], "Algún parametro de la ubicación, está vacio");
			res += destiny[i].trim() + " ";

		}
		return String.join(", ", res.trim().split(" "));

	}

	public SpaceShip(String data) throws DateTimeParseException{
		String[] temp = data.split(";");
		if (temp.length != 9) throw new IllegalArgumentException("Hay un error de formato en la linea ");
		this.checkNulls(temp);
		String[] destiny = temp[1].split("\\|");
		Checker.isEmptyString(destiny[0], "No hay suficientes datos para la ubicación ");
		Checker.isEmptyString(destiny[1], "No hay suficientes datos para la ubicación ");
		float temp2 = Float.parseFloat(temp[5].trim().replaceAll(",","."));
		Checker.isNegativeOrZero(Integer.parseInt(temp[8].trim()), "La duración no puede ser nula ");
		Checker.isNegativeOrZero(Integer.parseInt(temp[7].trim()), "No puede haber un numero nulo o negativo de pasajeros ");
		Checker.notByte(Integer.parseInt(temp[7]), "El numero de pasajeros no puede ser tan grande " );
		Checker.isNegativeOrZero((double) temp2, "El precio de producción no puede ser negativo ");
		Float prize = temp2;
		Integer crew = Integer.parseInt(temp[7].trim());
		Duration lengthOfExpedition = Duration.ofDays(Long.parseLong(temp[8].trim()));
		LocalDateTime datum = LocalDateTime.parse(temp[2].trim(), DateTimeFormatter.ofPattern("EEE MMM dd| yyyy HH:mm 'UTC'").withLocale(Locale.ENGLISH));
		String company = temp[0], details = temp[3], status = temp[4], rstatus = temp[6];
		this.company = company.trim();
		this.details = details.trim();
		this.rstatus = Rocket.valueOf(status.trim().toUpperCase());
		this.status = this.status(rstatus.trim());
		this.controlCenter = Checker.trimer(destiny[0].trim()) + ", " + Checker.trimer(destiny[1].trim());
		this.country = this.lastDestiny(destiny);
		this.datum = datum;
		this.prize = prize;
		this.crew = crew.byteValue();
		this.lengthOfExpedition = lengthOfExpedition;
	}
	
	public SpaceShip(String company, String controlCenter, String details, LocalDateTime datum, Float prize,
			String status, Rocket rstatus, Integer crew, Duration lengthOfExpedition) {
		this.checkNulls(company, controlCenter, details, datum, prize, status, rstatus, crew, lengthOfExpedition);
		Checker.isNegativeOrZero((double) prize, "El precio de producción no puede ser negativo");
		Checker.isNegativeOrZero(lengthOfExpedition, "La duración no puede ser nula");
		Checker.isNegativeOrZero(crew, "No puede haber un numero nulo o negativo de pasajeros");
		Checker.notByte(crew, "El numero de pasajeros no puede ser tan grande");
		String[] destiny = controlCenter.split("\\|");
		Checker.isEmptyString(destiny[0].trim(), "Algún parametro de la ubicación, está vacio");
		Checker.isEmptyString(destiny[1].trim(), "Algún parametro de la ubicación, está vacio");
		this.company = Checker.trimer(company);
		this.details = Checker.trimer(details);
		this.datum = datum;
		this.prize = prize;
		this.status = this.status(Checker.trimer(status));
		this.rstatus = rstatus;
		this.crew = crew.byteValue();
		this.lengthOfExpedition = lengthOfExpedition;
		this.controlCenter = Checker.trimer(destiny[0].trim()) + ", " + Checker.trimer(destiny[1].trim());
		this.country = this.lastDestiny(destiny);
	}

	public Duration getLengthOfExpedition() {
		return this.lengthOfExpedition;
	}

	public String getCompany() {
		return this.company;
	}
	public String getControlCenter() {
		return this.controlCenter;
	}

	public String getCountry() {
		return this.country;
	}
	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		Checker.isEmptyString(details, "Debe de rellenar este campo DETALLES");
		Checker.notNull(details);
		this.details = details;
	}

	public LocalDateTime getDatum() {
		return this.datum;
	}

	public Float getPrize() {
		return prize;
	}

	public void setPrize(Float prize) {
		Checker.notNull(prize);
		Checker.isNegativeOrZero((double) prize, "El precio debe ser positivo");
		this.prize = prize;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(String status) {
		Checker.notNull(status);
		this.status = this.status(status);
	}

	public Rocket getRstatus() {
		return this.rstatus;
	}

	public void setRstatus(Rocket rstatus) {
		Checker.notNull(rstatus);
		this.rstatus = rstatus;
	}

	public Integer getCrew() {
		return crew.intValue();
	}

	public void setCrew(Integer crew) {
		Checker.notNull(crew);
		Checker.isNegativeOrZero(crew, "No puede haber un numero nulo o negativo de pasajeros");
		Checker.notByte(crew, "El numero de pasajeros no puede ser tan grande");
		this.crew = crew.byteValue();
	}
	public int compareTo(SpaceShip o) {
		int res = (this.getLengthOfExpedition().compareTo(o.getLengthOfExpedition()) * 3
				+ this.getDatum().compareTo(o.getDatum()) - this.getReturnDate().compareTo(o.getReturnDate()))
				/ (this.getCrew() / o.getCrew() - (this.getCompany().compareTo(o.getCompany())));
		return res;
	}
	
	@Override
	public String toString() {
		String res = new String(this.getCompany() + " => " + this.controlCenter + " en " + this.country);
		res += this.details + "\n";
		res += "Fecha de despegue: " + this.getDatum() + "\t Fecha de retorno: " + this.getReturnDate();
		res += "\n Con una tripulación de: " + this.getCrew() + " tripulantes y en un estado: " + this.getRstatus().toString().charAt(0) + this.getRstatus().toString().substring(1).toLowerCase();
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if(obj instanceof SpaceShip) {
			SpaceShip temp = (SpaceShip)obj;
			res = this.getDatum().equals(temp.getDatum()) && this.getStatus().equals(temp.getStatus());
			res = res && this.getRstatus().equals(temp.getRstatus()) && this.getLengthOfExpedition().equals(temp.getLengthOfExpedition());
		}
		return res;
	}

	public LocalDateTime getReturnDate() {
		return this.getDatum().plus(this.getLengthOfExpedition());
	}

	public void setReturnDate(LocalDateTime dret) {
		Checker.notNull(dret);
		Duration temp = Duration.between(dret, this.getDatum());
		Checker.isNegativeOrZero(temp, "Pon una fecha posterior");
		this.lengthOfExpedition = temp;
	}
}
