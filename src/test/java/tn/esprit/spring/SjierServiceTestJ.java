package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class SkierTest {

    private Skier skier;

    @BeforeEach
    void setUp() {
        skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");

        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier.setSubscription(subscription);
        skier.setPistes(new HashSet<>());
    }

    @Test
    void testGetNumSkier() {
        assertEquals(1L, skier.getNumSkier());
    }

    @Test
    void testGetFirstName() {
        assertEquals("John", skier.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Doe", skier.getLastName());
    }

    @Test
    void testSetFirstName() {
        skier.setFirstName("Jane");
        assertEquals("Jane", skier.getFirstName());
    }

    @Test
    void testSetLastName() {
        skier.setLastName("Smith");
        assertEquals("Smith", skier.getLastName());
    }

    @Test
    void testGetSubscription() {
        assertNotNull(skier.getSubscription());
        assertEquals(TypeSubscription.ANNUAL, skier.getSubscription().getTypeSub());
    }

    @Test
    void testSetSubscription() {
        Subscription newSubscription = new Subscription();
        newSubscription.setTypeSub(TypeSubscription.MONTHLY);
        skier.setSubscription(newSubscription);
        assertEquals(TypeSubscription.MONTHLY, skier.getSubscription().getTypeSub());
    }

    @Test
    void testGetPistes() {
        assertNotNull(skier.getPistes());
        assertTrue(skier.getPistes().isEmpty());
    }

    @Test
    void testAddPiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);

        Set<Piste> pistes = skier.getPistes();
        pistes.add(piste);
        skier.setPistes(pistes);

        assertEquals(1, skier.getPistes().size());
        assertTrue(skier.getPistes().contains(piste));
    }

    @Test
    void testSkierConstructor() {
        Skier newSkier = new Skier();
        newSkier.setNumSkier(2L);
        newSkier.setFirstName("Alice");
        newSkier.setLastName("Brown");
        newSkier.setSubscription(new Subscription());
        newSkier.setPistes(new HashSet<>());

        assertEquals(2L, newSkier.getNumSkier());
        assertEquals("Alice", newSkier.getFirstName());
        assertEquals("Brown", newSkier.getLastName());
        assertNotNull(newSkier.getSubscription());
        assertTrue(newSkier.getPistes().isEmpty());
    }
}
