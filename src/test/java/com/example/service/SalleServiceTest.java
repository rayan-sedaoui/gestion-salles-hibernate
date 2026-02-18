package com.example.service;

import static org.junit.Assert.*;
import org.junit.Test;
import com.example.model.Salle;

public class SalleServiceTest {

    @Test
    public void testCreationSalle() {
        Salle salle = new Salle();
        salle.setNom("Salle A");
        salle.setCapacite(20);
        
        assertEquals("Salle A", salle.getNom());
        assertEquals(20, salle.getCapacite());
    }
}