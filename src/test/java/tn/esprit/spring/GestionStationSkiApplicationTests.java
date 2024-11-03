package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkierServiceImplTest {

	@InjectMocks
	private SkierServicesImpl skierService;

	@Mock
	private ISkierRepository skierRepository;

	@BeforeEach
	void setUp() {
		// Clear the repository before each test to ensure a clean state
		skierRepository.deleteAll();
	}

	@Test
	@Order(1)
	void testAddSkier() {
		// Arrange
		Skier skier = new Skier();
		skier.setFirstName("rania");
		skier.setLastName("hmidet");
		skier.setDateOfBirth(LocalDate.of(2001, 4, 2));
		skier.setCity("tunis");

		Subscription subscription = new Subscription();
		subscription.setStartDate(LocalDate.of(2023, 11, 1));
		subscription.setTypeSub(TypeSubscription.ANNUAL);

		skier.setSubscription(subscription);

		when(skierRepository.save(any(Skier.class))).thenReturn(skier);

		// Act
		Skier result = skierService.addSkier(skier);
		System.out.println("Added skier: " + result);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getSubscription().getEndDate());
		verify(skierRepository).save(any(Skier.class));
	}

	@Test
	@Order(2)
	void testRetrieveAllSkiers() {
		// Arrange
		List<Skier> skiers = new ArrayList<>();
		skiers.add(new Skier(1L, "amine", "ben mohamed", LocalDate.of(1993, 5, 15), "Tunis", null, null, null));
		skiers.add(new Skier(2L, "rim", "hmidet", LocalDate.of(1998, 8, 22), "Monastir", null, null, null));

		when(skierRepository.findAll()).thenReturn(skiers);

		// Act
		List<Skier> result = skierService.retrieveAllSkiers();
		System.out.println("Retrieved skiers: " + result);

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("amine", result.get(0).getFirstName());
		assertEquals("hmidet", result.get(1).getLastName());
		verify(skierRepository).findAll();
	}
}

