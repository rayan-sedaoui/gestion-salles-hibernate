package com.example;

import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.SalleService;
import com.example.service.UtilisateurService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        // Création de l'EntityManagerFactory (allume la connexion à la base)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-salles");
        
        // Création des services
        UtilisateurService utilisateurService = new UtilisateurService(emf);
        SalleService salleService = new SalleService(emf);
        
        try {
            // Test des opérations pour Utilisateur
            System.out.println("\n=== Test CRUD Utilisateur ===");
            testCrudUtilisateur(utilisateurService);
            
            // Test des opérations pour Salle
            System.out.println("\n=== Test CRUD Salle ===");
            testCrudSalle(salleService);
            
        } finally {
            // Fermeture propre de la connexion
            if (emf != null) emf.close();
        }
    }
    
    private static void testCrudUtilisateur(UtilisateurService service) {
        System.out.println("Création d'utilisateurs...");
        Utilisateur u1 = new Utilisateur("Dupont", "Jean", "jean.dupont@example.com");
        u1.setDateNaissance(LocalDate.of(1985, 5, 15));
        u1.setTelephone("+33612345678");
        
        Utilisateur u2 = new Utilisateur("Martin", "Sophie", "sophie.martin@example.com");
        u2.setDateNaissance(LocalDate.of(1990, 10, 20));
        
        service.save(u1);
        service.save(u2);
        
        System.out.println("\nLecture de tous les utilisateurs :");
        service.findAll().forEach(System.out::println);
    }
    
    private static void testCrudSalle(SalleService service) {
        System.out.println("Création de salles...");
        Salle s1 = new Salle("Salle A101", 30);
        s1.setEtage(1);
        
        Salle s2 = new Salle("Amphithéâtre B201", 150);
        s2.setEtage(2);
        
        service.save(s1);
        service.save(s2);
        
        System.out.println("\nLecture de toutes les salles :");
        service.findAll().forEach(System.out::println);
    }
}