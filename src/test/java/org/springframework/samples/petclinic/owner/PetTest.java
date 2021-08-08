package org.springframework.samples.petclinic.owner;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visit.Visit;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PetTest {

	private Pet petInstance;

	private static final int TEST_OWNER_ID = 1;

	private PetType petTypeInstance;

	private OwnerRepository owners;

	private static final LocalDate birthDate = LocalDate.of(1998, 10, 6);
	private static Visit visit1 = new Visit().setDate(LocalDate.of(1998, 10, 05));
	private static Visit visit2 = new Visit().setDate(LocalDate.of(1998, 10, 06));
	private static Visit visit3 = new Visit().setDate(LocalDate.of(1998, 10, 07));
	private static Visit visit4 = new Visit().setDate(LocalDate.of(1999, 10, 06));
	private static Visit visit5 = new Visit().setDate(LocalDate.of(1999, 10, 07));
	private static Visit visit6 = new Visit().setDate(LocalDate.of(2000, 10, 07));
	private static Visit visit7 = new Visit().setDate(LocalDate.of(2001, 10, 07));

	private static List<Visit> visits = new ArrayList<>(Arrays.asList(visit1, visit2, visit3, visit4, visit5, visit6, visit7));


	@BeforeEach
	public void setup() {
		petInstance = new Pet();
		setUpTypes();
		setUpOwners();
		setUpVisits();
	}

	public void setUpVisits() {
		for (Visit visit: visits) {
			petInstance.addVisit(visit);
		}
	}

	public void setUpOwners() {
		owners = mock(OwnerRepository.class);
		when(this.owners.findById(TEST_OWNER_ID))
			.thenReturn(new Owner());
	}

	private void setUpTypes() {
		petTypeInstance = mock(PetType.class);
		when(this.petTypeInstance.getName())
			.thenReturn("Houman");
		when(this.petTypeInstance.getId())
			.thenReturn(0);
	}

	@Test
	public void testGetVisits() {
		assertTrue(petInstance.getVisits().size() == visits.size() &&
			petInstance.getVisits().containsAll(visits) && visits.containsAll(petInstance.getVisits()));
	}

	@Test
	public void testGetVisitsUntilAgeBeforeBirthDate() {
		petInstance.setBirthDate(birthDate);
		assertTrue(petInstance.getVisitsUntilAge(1).size() == visits.subList(1, 3).size() &&
			petInstance.getVisitsUntilAge(1).containsAll(visits.subList(1, 3)) &&
			visits.subList(1, 3).containsAll(petInstance.getVisitsUntilAge(1)));
	}

	@Test
	public void testGetVisitsUntilAgeNormal() {
		petInstance.setBirthDate(birthDate);
		assertTrue(petInstance.getVisitsUntilAge(1).size() == visits.subList(0, 3).size() &&
			petInstance.getVisitsUntilAge(1).containsAll(visits.subList(0, 3)) &&
			visits.subList(0, 3).containsAll(petInstance.getVisitsUntilAge(1)));
	}

	@Test
	public void testGetVisitsBetweenIndDay() {
		LocalDate localDate1 = LocalDate.of(1998, 10, 06);
		List<Visit> emptyVisitList = new ArrayList<>();
		petInstance.setBirthDate(birthDate);
		assertTrue(petInstance.getVisitsBetween(localDate1, localDate1).size() == emptyVisitList.size() &&
			petInstance.getVisitsBetween(localDate1, localDate1).equals(emptyVisitList)
			);
	}

	@Test
	public void testGetVisitsBetween() {
		LocalDate localDate1 = LocalDate.of(1998, 10, 06);
		LocalDate localDate2 = LocalDate.of(2001, 10, 07);
		petInstance.setBirthDate(birthDate);
		assertTrue(petInstance.getVisitsBetween(localDate1, localDate2).size() == visits.subList(2, 6).size() &&
			petInstance.getVisitsBetween(localDate1, localDate2).containsAll(visits.subList(2, 6)) &&
			visits.subList(2, 6).containsAll(petInstance.getVisitsBetween(localDate1, localDate2)));
	}

	@Test
	public void testAddVisit() {
		Visit visit = new Visit();
		List<Visit> visitsBefore = petInstance.getVisits();
		List<Visit> visitsAfter = new ArrayList<>(visitsBefore);
		visitsAfter.add(visit);
		petInstance.addVisit(visit);
		assertTrue(petInstance.getVisits().size() == visitsAfter.size() &&
			petInstance.getVisits().containsAll(visitsAfter) && visitsAfter.containsAll(petInstance.getVisits()));
	}

	@Test
	public void testRemoveVisit() {
		List<Visit> visitsBefore = petInstance.getVisits();
		petInstance.removeVisit(visit1);
		List<Visit> visitsAfter = new ArrayList<>(visitsBefore);
		visitsAfter.remove(visit1);
		assertTrue(petInstance.getVisits().size() == visitsAfter.size() &&
			petInstance.getVisits().containsAll(visitsAfter) && visitsAfter.containsAll(petInstance.getVisits()));
	}

	@Test
	public void testSetGetOwner() {
		petInstance.setOwner(owners.findById(TEST_OWNER_ID));
		assertEquals(petInstance.getOwner(), owners.findById(TEST_OWNER_ID));
	}

	@Test
	public void testSetGetBirthDate() {
		petInstance.setBirthDate(birthDate);
		assertEquals(petInstance.getBirthDate().getYear(), birthDate.getYear());
		assertEquals(petInstance.getBirthDate().getMonthValue(), birthDate.getMonthValue());
		assertEquals(petInstance.getBirthDate().getDayOfMonth(), birthDate.getDayOfMonth());
	}

	@Test
	public void testSetGetPetType() {
		petInstance.setType(petTypeInstance);
		assertEquals(petInstance.getType().getId(), 0);
		assertEquals(petInstance.getType().getName(), "Houman");
	}

	@Test
	public void testThreadSafety() throws ExecutionException, InterruptedException {
		int threads = 10;
		ExecutorService service = Executors.newFixedThreadPool(threads);
		Collection<Future<Integer>> futures = new ArrayList<>(threads);
		for (int t = 0; t < threads; ++t) {
			futures.add(service.submit(() ->{
				petInstance.addVisit(new Visit());
				return petInstance.getVisits().size();
			}));
		}
		Set<Integer> ids = new HashSet<>();
		for (Future<Integer> f : futures) {
			ids.add(f.get());
		}
		assertEquals(ids.size(), threads);
	}


}
