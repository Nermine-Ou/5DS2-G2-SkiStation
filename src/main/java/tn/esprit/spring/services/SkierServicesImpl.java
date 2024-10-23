package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class SkierServicesImpl implements ISkierServices {

    private static final Logger logger = LogManager.getLogger(SkierServicesImpl.class);

    private ISkierRepository skierRepository;
    private IPisteRepository pisteRepository;
    private ICourseRepository courseRepository;
    private IRegistrationRepository registrationRepository;
    private ISubscriptionRepository subscriptionRepository;

    @Override
    public List<Skier> retrieveAllSkiers() {
        logger.info("Retrieving all skiers from the database.");
        List<Skier> skiers = skierRepository.findAll();
        logger.info("Retrieved {} skiers.", skiers.size());
        return skiers;
    }

    @Override
    public Skier addSkier(Skier skier) {
        logger.info("Adding a new skier: {} {}", skier.getFirstName(), skier.getLastName());
        switch (skier.getSubscription().getTypeSub()) {
            case ANNUAL:
                skier.getSubscription().setEndDate(skier.getSubscription().getStartDate().plusYears(1));
                break;
            case SEMESTRIEL:
                skier.getSubscription().setEndDate(skier.getSubscription().getStartDate().plusMonths(6));
                break;
            case MONTHLY:
                skier.getSubscription().setEndDate(skier.getSubscription().getStartDate().plusMonths(1));
                break;
        }
        logger.debug("Skier's subscription type: {}, End Date: {}", skier.getSubscription().getTypeSub(), skier.getSubscription().getEndDate());
        Skier savedSkier = skierRepository.save(skier);
        logger.info("Skier added with ID: {}", savedSkier.getNumSkier());
        return savedSkier;
    }

    @Override
    public Skier assignSkierToSubscription(Long numSkier, Long numSubscription) {
        logger.info("Assigning subscription {} to skier {}", numSubscription, numSkier);
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        Subscription subscription = subscriptionRepository.findById(numSubscription).orElse(null);
        if (skier != null && subscription != null) {
            skier.setSubscription(subscription);
            logger.debug("Skier {} assigned to subscription {}", numSkier, numSubscription);
            Skier updatedSkier = skierRepository.save(skier);
            logger.info("Skier {} successfully updated with new subscription.", numSkier);
            return updatedSkier;
        } else {
            logger.error("Failed to assign subscription. Skier or Subscription not found for assignment.");
            return null;
        }
    }

    @Override
    public Skier addSkierAndAssignToCourse(Skier skier, Long numCourse) {
        logger.info("Adding skier and assigning to course {}", numCourse);
        Skier savedSkier = skierRepository.save(skier);
        Course course = courseRepository.getById(numCourse);
        Set<Registration> registrations = savedSkier.getRegistrations();
        for (Registration r : registrations) {
            r.setSkier(savedSkier);
            r.setCourse(course);
            registrationRepository.save(r);
            logger.debug("Registration saved for skier {} and course {}", savedSkier.getNumSkier(), course.getNumCourse());
        }
        logger.info("Skier {} added and assigned to course {}", savedSkier.getNumSkier(), numCourse);
        return savedSkier;
    }

    @Override
    public void removeSkier(Long numSkier) {
        logger.info("Removing skier with id {}", numSkier);
        skierRepository.deleteById(numSkier);
        logger.info("Skier {} removed from the database.", numSkier);
    }

    @Override
    public Skier retrieveSkier(Long numSkier) {
        logger.info("Retrieving skier with id {}", numSkier);
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        if (skier != null) {
            logger.info("Skier found: {} {}", skier.getFirstName(), skier.getLastName());
        } else {
            logger.warn("Skier with id {} not found.", numSkier);
        }
        return skier;
    }

    @Override
    public Skier assignSkierToPiste(Long numSkieur, Long numPiste) {
        logger.info("Assigning piste {} to skier {}", numPiste, numSkieur);
        Skier skier = skierRepository.findById(numSkieur).orElse(null);
        Piste piste = pisteRepository.findById(numPiste).orElse(null);

        if (skier == null) {
            logger.error("Skier with ID {} not found.", numSkieur);
            return null;
        }

        if (piste == null) {
            logger.error("Piste with ID {} not found.", numPiste);
            return null;
        }

        try {
            if (skier.getPistes() != null) {
                skier.getPistes().add(piste);
                logger.debug("Piste {} assigned to skier {}", numPiste, numSkieur);
            } else {
                logger.warn("Piste list was null, initializing new set for skier {}", numSkieur);
                Set<Piste> pisteList = new HashSet<>();
                pisteList.add(piste);
                skier.setPistes(pisteList);
            }
        } catch (Exception e) {
            logger.error("Error assigning piste {} to skier {}: {}", numPiste, numSkieur, e.getMessage());
            return null;
        }

        Skier updatedSkier = skierRepository.save(skier);
        logger.info("Skier {} successfully assigned to piste {}", numSkieur, numPiste);
        return updatedSkier;
    }


    @Override
    public List<Skier> retrieveSkiersBySubscriptionType(TypeSubscription typeSubscription) {
        logger.info("Retrieving skiers with subscription type {}", typeSubscription);
        List<Skier> skiers = skierRepository.findBySubscription_TypeSub(typeSubscription);
        logger.info("Retrieved {} skiers with subscription type {}", skiers.size(), typeSubscription);
        return skiers;
    }
}
