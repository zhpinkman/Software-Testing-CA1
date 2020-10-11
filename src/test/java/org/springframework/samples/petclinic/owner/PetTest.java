package org.springframework.samples.petclinic.owner;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PetTest {

	private Pet petInstance;

	private static final LocalDate birthDate = LocalDate.of(1999, 10, 6);


	@BeforeEach
	public void setup() {
		petInstance = new Pet();
	}

	@Test
	public void testSetGetBirthDate() {
		petInstance.setBirthDate(birthDate);
		assertEquals(petInstance.getBirthDate().getYear(), birthDate.getYear());
		assertEquals(petInstance.getBirthDate().getMonthValue(), birthDate.getMonthValue());
		assertEquals(petInstance.getBirthDate().getDayOfMonth(), birthDate.getDayOfMonth());
	}

//	public void setUpMockPet() {
//		petMockObject = mock(Pet.class);
//	}

}
