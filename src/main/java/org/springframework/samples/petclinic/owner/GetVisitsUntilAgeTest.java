package org.springframework.samples.petclinic.owner;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class GetVisitsUntilAgeTest {

	private int age;

	private List<Visit> resultVisits;
	private Pet petInstance;

	private final LocalDate birthDate = LocalDate.of(1998, 10, 06);

	static Visit visit1 = new Visit().setDate(LocalDate.of(1998, 10, 05));
	static Visit visit2 = new Visit().setDate(LocalDate.of(1998, 10, 06));
	static Visit visit3 = new Visit().setDate(LocalDate.of(1998, 10, 07));
	static Visit visit4 = new Visit().setDate(LocalDate.of(1999, 10, 06));
	static Visit visit5 = new Visit().setDate(LocalDate.of(1999, 10, 07));
	static Visit visit6 = new Visit().setDate(LocalDate.of(2000, 10, 07));
	static Visit visit7 = new Visit().setDate(LocalDate.of(2001, 10, 07));

	static List<Visit> visits = new ArrayList<>(Arrays.asList(visit1, visit2, visit3, visit4, visit5, visit6, visit7));


	@Before
	public void setUp() {
		petInstance = new Pet();
		petInstance.setBirthDate(birthDate);
		for (Visit visit: visits) {
			petInstance.addVisit(visit);
		}
	}

	public GetVisitsUntilAgeTest(int age, List<Visit> visits) {
		this.age = age;
		this.resultVisits = visits;
	}

	@Parameterized.Parameters(name = "{index} : visits until age {0} = {1}")
	public static Iterable<Object []> data()
	{
		return Arrays.asList(new Object[][] {
		{1, }
		});
	}
}
