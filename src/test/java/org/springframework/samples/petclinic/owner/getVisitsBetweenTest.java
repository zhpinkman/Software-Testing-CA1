package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.samples.petclinic.visit.Visit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

@RunWith(Theories.class)
public class getVisitsBetweenTest {

	private Pet petInstance;

	private final LocalDate first_date = LocalDate.of(1998, 10, 06);
	private final LocalDate second_date = LocalDate.of(2002, 5, 02);


	@Before
	public void setUp() {
		petInstance = new Pet();
	}

	@DataPoints("Year Values")
	public static int[] Yvalues() {
		int [] ints = {1998, 1999, 2000, 2002, 2003};
		return ints;
	}

	@DataPoints("Month Values")
	public static int[] Mvalues() {
		int [] ints = {10, 01, 05};
		return ints;
	}

	@DataPoints("Day Values")
	public static int[] Dvalues() {
		int [] ints = {01, 05, 06, 07, 03};
		return ints;
	}

	@Theory
	public void testDatesBetween(@FromDataPoints("Year Values") int year,
									   @FromDataPoints("Month Values") int month,
								 		@FromDataPoints("Day Values") int day) {
		boolean result;
		Visit visit1 = new Visit().setDate(LocalDate.of(year, month, day));
		petInstance.addVisit(visit1);
		result = visit1.getDate().isAfter(first_date) && visit1.getDate().isBefore(second_date);
		int int_result = result ? 1 : 0;
		assertEquals(petInstance.getVisitsBetween(first_date, second_date).size(), int_result);
	}
}
