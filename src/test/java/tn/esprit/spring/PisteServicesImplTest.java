package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class PisteServicesImplTest {

	@Mock
	private IPisteRepository pisteRepository;

	@InjectMocks
	private PisteServicesImpl pisteServices;

	private Piste piste;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		piste = new Piste(1L, "Blue Trail", Color.BLUE, 3000, 45, null);
	}

	@Test
	public void testRetrieveAllPistes() {
		List<Piste> pistes = Arrays.asList(piste);
		when(pisteRepository.findAll()).thenReturn(pistes);

		List<Piste> retrievedPistes = pisteServices.retrieveAllPistes();

		assertEquals(1, retrievedPistes.size());
		verify(pisteRepository, times(1)).findAll();
	}

	@Test
	public void testAddPiste() {
		when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

		Piste addedPiste = pisteServices.addPiste(piste);

		assertEquals(piste.getNumPiste(), addedPiste.getNumPiste());
		verify(pisteRepository, times(1)).save(piste);
	}

	@Test
	public void testRemovePiste() {
		Long numPiste = piste.getNumPiste();

		pisteServices.removePiste(numPiste);

		verify(pisteRepository, times(1)).deleteById(numPiste);
	}

	@Test
	public void testRetrievePiste() {
		when(pisteRepository.findById(piste.getNumPiste())).thenReturn(Optional.of(piste));

		Piste retrievedPiste = pisteServices.retrievePiste(piste.getNumPiste());

		assertEquals(piste.getNumPiste(), retrievedPiste.getNumPiste());
		verify(pisteRepository, times(1)).findById(piste.getNumPiste());
	}

	@Test
	public void testRetrievePisteNotFound() {
		Long numPiste = 2L;
		when(pisteRepository.findById(numPiste)).thenReturn(Optional.empty());

		Piste retrievedPiste = pisteServices.retrievePiste(numPiste);

		assertNull(retrievedPiste);
		verify(pisteRepository, times(1)).findById(numPiste);
	}
}
