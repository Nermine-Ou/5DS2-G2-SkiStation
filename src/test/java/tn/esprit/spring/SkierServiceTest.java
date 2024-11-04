package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
 class SkierServiceTest {

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServicesImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testRetrieveAllSkiers() {
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier());

        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> retrievedSkiers = skierServicesImpl.retrieveAllSkiers();

        assertEquals(1, retrievedSkiers.size());
        verify(skierRepository, times(1)).findAll();
    }

    @Test
     void testAddSkier() {
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        Skier skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setSubscription(subscription);

        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier addedSkier = skierServicesImpl.addSkier(skier);

        assertNotNull(addedSkier);
        assertEquals("John", addedSkier.getFirstName());
        assertEquals("Doe", addedSkier.getLastName());
        assertEquals(subscription.getEndDate(), skier.getSubscription().getStartDate().plusYears(1));
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
     void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        Long numSkier = 1L;
        Long numSubscription = 2L;

        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(numSubscription)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServicesImpl.assignSkierToSubscription(numSkier, numSubscription);

        assertNotNull(result);
        assertEquals(subscription, result.getSubscription());
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
     void testAssignSkierToSubscriptionSkierNotFound() {
        Long numSkier = 1L;
        Long numSubscription = 2L;

        when(skierRepository.findById(numSkier)).thenReturn(Optional.empty());

        Skier result = skierServicesImpl.assignSkierToSubscription(numSkier, numSubscription);

        assertNull(result);
        verify(skierRepository, times(0)).save(any(Skier.class));
    }

    @Test
     void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        Set<Registration> registrations = new java.util.HashSet<>();
        registrations.add(new Registration());
        skier.setRegistrations(registrations);
        Long numCourse = 1L;
        Course course = new Course();

        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        when(courseRepository.getById(numCourse)).thenReturn(course);

        Skier result = skierServicesImpl.addSkierAndAssignToCourse(skier, numCourse);

        assertNotNull(result);
        assertEquals(skier, result);
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
     void testRemoveSkier() {
        Long numSkier = 1L;

        doNothing().when(skierRepository).deleteById(numSkier);

        skierServicesImpl.removeSkier(numSkier);

        verify(skierRepository, times(1)).deleteById(numSkier);
    }

    @Test
     void testRetrieveSkier() {
        Long numSkier = 1L;
        Skier skier = new Skier();

        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));

        Skier retrievedSkier = skierServicesImpl.retrieveSkier(numSkier);

        assertNotNull(retrievedSkier);
        verify(skierRepository, times(1)).findById(numSkier);
    }

    @Test
     void testAssignSkierToPiste() {
        Long numSkieur = 1L;
        Long numPiste = 2L;
        Skier skier = new Skier();
        Piste piste = new Piste();

        when(skierRepository.findById(numSkieur)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServicesImpl.assignSkierToPiste(numSkieur, numPiste);

        assertNotNull(result);
        assertTrue(result.getPistes().contains(piste));
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
     void testRetrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier());

        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(skiers);

        List<Skier> result = skierServicesImpl.retrieveSkiersBySubscriptionType(typeSubscription);

        assertEquals(1, result.size());
        verify(skierRepository, times(1)).findBySubscription_TypeSub(typeSubscription);
    }
}
