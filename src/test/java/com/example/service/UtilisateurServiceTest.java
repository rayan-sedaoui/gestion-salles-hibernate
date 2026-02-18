package com.example.service;

import com.example.model.Utilisateur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

public class UtilisateurServiceTest {
    
    private EntityManagerFactory emf;
    private UtilisateurService service;
    
    @Before
    public void setUp() {
        // Initialise la base avant chaque test
        emf = Persistence.createEntityManagerFactory("gestion-salles");
        service = new UtilisateurService(emf);
    }
    
    @After
    public void tearDown() {
        // Ferme la connexion après chaque test
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
    
    @Test
    public void testCrudOperations() {
        Utilisateur utilisateur = new Utilisateur("Test", "User", "test.user@example.com");
        Utilisateur savedUtilisateur = service.save(utilisateur);
        assertNotNull(savedUtilisateur.getId());
        
        Optional<Utilisateur> foundUtilisateur = service.findById(savedUtilisateur.getId());
        assertTrue(foundUtilisateur.isPresent());
        assertEquals("Test", foundUtilisateur.get().getNom());
    }

    @Test
    public void testFindByEmail() {
        Utilisateur utilisateur = new Utilisateur("Email", "Test", "email.test@example.com");
        service.save(utilisateur);
        
        Optional<Utilisateur> foundUtilisateur = service.findByEmail("email.test@example.com");
        assertTrue(foundUtilisateur.isPresent());
    }
}