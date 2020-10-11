package org.springframework.samples.petclinic.owner;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PetTest {

	private Pet petInstance;

	private static final int TEST_OWNER_ID = 1;

	private PetType petTypeInstance;

	private OwnerRepository owners;

	private static final LocalDate birthDate = LocalDate.of(1999, 10, 6);


	@BeforeEach
	public void setup() {
		petInstance = new Pet();
		setUpTypes();
		setOwners();
	}

	public void setOwners() {
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
	public void testAddVisit() {
		Visit visit = new Visit();
		petInstance.addVisit(visit);
		List<Visit> visits = petInstance.getVisits();
		assertEquals(visits.size(), 1);
		assertEquals(visits.get(0), visit);
	}

	@Test
	public void testRemoveVisit() {
		Visit visit = new Visit();
		petInstance.addVisit(visit);
		List<Visit> visits = petInstance.getVisits();
		assertEquals(visits.size(), 1);
		petInstance.removeVisit(visit);
		List<Visit> visitsAfterRemoval = petInstance.getVisits();
		assertEquals(visitsAfterRemoval.size(), 0);
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

//	public void setUpMockPet() {
//		petMockObject = mock(Pet.class);
//	}

}
