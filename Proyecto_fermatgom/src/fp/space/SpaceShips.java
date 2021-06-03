package fp.space;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceShips {
	List<SpaceShip> spaceShips = new LinkedList<SpaceShip>();

	public SpaceShips() {
	}

	public SpaceShips(Stream<SpaceShip> ships) {
		this.spaceShips = new LinkedList<SpaceShip>(ships.collect(Collectors.toList()));
	}

	public SpaceShips(List<SpaceShip> ships) {
		spaceShips = new LinkedList<SpaceShip>(ships);
	}

	public boolean addSpaceShips(List<SpaceShip> ships) {
		return spaceShips.addAll(ships);
	}

	public boolean addSpaceShips(Stream<SpaceShip> ships) {
		return spaceShips.addAll(ships.collect(Collectors.toList()));
	}

	public boolean addSpaceShips(SpaceShip ship) {
		return spaceShips.add(ship);
	}

	@Override
	public String toString() {
		return "SpaceShips [spaceShips=" + spaceShips + "]";
	}

	public List<SpaceShip> getSpaceShips() {
		return spaceShips;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spaceShips == null) ? 0 : spaceShips.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpaceShips other = (SpaceShips) obj;
		if (spaceShips == null) {
			if (other.spaceShips != null)
				return false;
		} else if (!spaceShips.equals(other.spaceShips))
			return false;
		return true;
	}

	/**
	 * 
	 * @param rocket
	 * @param datum
	 * @return Devuelve una lista invertida que contenga el mismo tipo de cohete
	 */
	public Collection<SpaceShip> filterByRockerDatumI(Rocket rocket) {
		return this.spaceShips.stream()
				.sorted(Comparator.comparing(SpaceShip::getDatum).reversed())
				.filter(v ->v.getRstatus().equals(rocket))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param prize
	 */
	public boolean anySpaceShipWithLessPrize(Float prize) {
		return this.spaceShips.stream().anyMatch(v -> v.getPrize().compareTo(prize) < 0);
	}

	/**
	 * 
	 * @param sp
	 */
	public static boolean anySpaceShipWithATypeOfStatus(SpaceShips sp) {
		return sp.getSpaceShips().stream().allMatch(v -> v.getStatus());
	}

	public long averageDurationPatched(LocalDateTime ldti, LocalDateTime ldtf, int n) {
		return (long) this.spaceShips.stream()
				.filter(v -> !v.getDatum().equals(null) && v.getDatum().compareTo(ldti) >= 0 && v.getDatum().compareTo(ldtf) <= 0).limit(n)
				.mapToDouble(v -> (double) v.getLengthOfExpedition().get(ChronoUnit.DAYS)).average().orElse(0);
	}

	public Map<String, List<String>> getMapofCountryByCompany() {
		return this.spaceShips.stream()
				.collect(Collectors.groupingBy(SpaceShip::getCompany, Collectors.collectingAndThen(Collectors.toList(),
						x -> x.stream().map(SpaceShip::getCountry).distinct().collect(Collectors.toList()))));
	}

	public SpaceShip getLongestDurationSpaceShip() {
		return this.spaceShips.stream().max(Comparator.comparing(SpaceShip::getLengthOfExpedition))
				.orElseThrow(NoSuchElementException::new);
	}

	public Collection<SpaceShip> changeStatusByLongestDurationSpaceShipSortedByStatus() {
		Rocket tempVal = this.getLongestDurationSpaceShip().getRstatus();
		List<SpaceShip> temp = new LinkedList<SpaceShip>(spaceShips);
		temp.stream().sorted(Comparator.comparing(SpaceShip::getRstatus))
				.forEach(x -> x.setRstatus(tempVal));
		return temp;
	}

	public Collection<SpaceShip> getCheapestAndExpensiveRocket() {
		List<SpaceShip> temp = new ArrayList<SpaceShip>(2);
		temp.add(this.spaceShips.stream().max(Comparator.comparing(SpaceShip::getPrize)).orElseThrow(NoSuchElementException::new));
		temp.add(this.spaceShips.stream().min(Comparator.comparing(SpaceShip::getPrize)).orElseThrow(NoSuchElementException::new));
		return temp;
	}

	public Map<Rocket, Integer> getMapNumberOfEachRocketStatus() {
		return this.spaceShips.stream().collect(Collectors.groupingBy(SpaceShip::getRstatus,
				Collectors.collectingAndThen(Collectors.counting(), x -> x.intValue())));
	}

	public Map<Boolean, List<LocalDateTime>> getMapDatesByStatusOfRocketAfterDate(LocalDateTime datum) {
		return this.spaceShips.stream().collect(Collectors.groupingBy(SpaceShip::getStatus, Collectors.filtering(
				v -> v.getDatum().compareTo(datum) > 0, Collectors.mapping(SpaceShip::getDatum, Collectors.toList()))));
	}

	public Map<LocalDateTime, Map<Rocket, TreeSet<SpaceShip>>> getMapSpaceShipsByTypesOfRocketByDate() {
		return this.spaceShips.stream().collect(Collectors.groupingBy(SpaceShip::getDatum,
				Collectors.groupingBy(SpaceShip::getRstatus, Collectors.toCollection(TreeSet<SpaceShip>::new))

		));
	}

	public Map<String, List<String>> getCompanyByControlCenter() {
		return this.spaceShips.stream()
				.collect(Collectors.groupingBy(SpaceShip::getCompany, Collectors.collectingAndThen(Collectors.toList(),
						x -> x.stream().map(SpaceShip::getControlCenter).distinct().collect(Collectors.toList()))));

	}

}
