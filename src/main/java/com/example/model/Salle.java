package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    @Column(nullable = false)
    private String nom;

    @Min(value = 1, message = "La capacité doit être d'au moins 1")
    private int capacite;

    private boolean disponible = true; // Par défaut disponible
    
    private String description;
    private int etage;

    public Salle() {}

    // Nouveau constructeur pour correspondre à ton test
    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getEtage() { return etage; }
    public void setEtage(int etage) { this.etage = etage; }

    @Override
    public String toString() {
        return "Salle [id=" + id + ", nom=" + nom + ", capacite=" + capacite + ", disponible=" + disponible + "]";
    }
}