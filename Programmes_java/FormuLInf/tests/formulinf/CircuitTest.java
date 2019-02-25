package formulinf;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;
import java.util.LinkedList;

/**
   Pour compiler et exécuter en ligne de commande, après compilation de Bolide.java
   javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar CircuitTest.java
   java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CircuitTest
*/

public class CircuitTest {

    Circuit c;
    Bolide b;
    boolean[][] carte;
    
    @Before
    public void initBolide() {
        carte = new boolean[6][10];
        for (int x=0; x<6; x++) {
            for (int y=0; y<10; y++) {
                carte[x][y] = false;
            }
        }
        for (int x=1; x<3; x++) {
            for (int y=2; y<7; y++) {
                carte[x][y] = true;
            }
        }
        carte[3][7] = true;
        carte[4][7] = true;
        carte[4][8] = true;
        c = new Circuit(6, 10, carte, 1, 3);
        c.bolide.vitesse = new Vect(1, 2);
    }

    // Tests du constructeur
    @Test
    public void circuitSimple1() {
        Circuit c0 = new Circuit(6, 10, carte, 1, 3);
        assertEquals(6, c0.nbLignes);
        assertEquals(10, c0.nbColonnes);
        assertEquals(new Vect(1, 3), c0.bolide.position);
        assertEquals(Vect.ZERO, c0.bolide.vitesse);
        assertFalse(c0.terrain[0][4].traversable);
        assertTrue(c0.terrain[1][4].traversable);
        assertTrue(c0.terrain[2][4].traversable);
        assertFalse(c0.terrain[3][4].traversable);
        assertFalse(c0.terrain[4][4].traversable);
        assertFalse(c0.terrain[5][4].traversable);
    }
    @Test
    public void circuitSimple2() {
        Circuit c0 = new Circuit(6, 10, carte, 1, 1);
        assertEquals(6, c0.nbLignes);
        assertEquals(10, c0.nbColonnes);
        assertEquals(new Vect(1, 1), c0.bolide.position);
        assertEquals(Vect.ZERO, c0.bolide.vitesse);
        assertFalse(c0.terrain[0][4].traversable);
        assertTrue(c0.terrain[1][4].traversable);
        assertTrue(c0.terrain[2][4].traversable);
        assertFalse(c0.terrain[3][4].traversable);
        assertFalse(c0.terrain[4][4].traversable);
        assertFalse(c0.terrain[5][4].traversable);
    }
    @Test
    public void circuitDepartHorsLimites1() {
    	Circuit c0 = null;
    	try {
    		c0 = new Circuit(6, 10, carte, 1, 12);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertNull(c0);
    	}
    }
    @Test
    public void circuitDepartHorsLimites2() {
        Circuit c0 = null;
        try {
        	c0 = new Circuit(6, 10, carte, 6, -3);
        	fail();
        }
        catch (IllegalArgumentException e) {
        	assertNull(c0);
        }
    }
    @Test
    public void circuitCarteTropGrande() {
        Circuit c0 = new Circuit(5, 8, carte, 1, 3);
        assertEquals(5, c0.nbLignes);
        assertEquals(8, c0.nbColonnes);
        assertEquals(new Vect(1, 3), c0.bolide.position);
        assertEquals(Vect.ZERO, c0.bolide.vitesse);
        assertFalse(c0.terrain[0][4].traversable);
        assertTrue(c0.terrain[1][4].traversable);
        assertTrue(c0.terrain[2][4].traversable);
        assertFalse(c0.terrain[3][4].traversable);
        assertFalse(c0.terrain[4][4].traversable);
    }
    @Test
    public void circuitCarteTropPetite1() {
        Circuit c0 = new Circuit(8, 10, carte, 1, 3);
        assertEquals(8, c0.nbLignes);
        assertEquals(10, c0.nbColonnes);
        assertEquals(new Vect(1, 3), c0.bolide.position);
        assertEquals(Vect.ZERO, c0.bolide.vitesse);
        assertFalse(c0.terrain[0][4].traversable);
        assertTrue(c0.terrain[1][4].traversable);
        assertTrue(c0.terrain[2][4].traversable);
        assertFalse(c0.terrain[3][4].traversable);
        assertFalse(c0.terrain[4][4].traversable);
        assertFalse(c0.terrain[5][4].traversable);
        assertFalse(c0.terrain[6][4].traversable);
        assertFalse(c0.terrain[7][4].traversable);
    }
    @Test
    public void circuitCarteTropPetite2() {
        boolean[] ligne2 = new boolean[3];
        ligne2[0] = false;
        ligne2[1] = true;
        ligne2[2] = true;
        carte[2] = ligne2;
        Circuit c0 = new Circuit(6, 10, carte, 1, 3);
        assertEquals(6, c0.nbLignes);
        assertEquals(10, c0.nbColonnes);
        assertEquals(new Vect(1, 3), c0.bolide.position);
        assertEquals(Vect.ZERO, c0.bolide.vitesse);
        assertFalse(c0.terrain[0][4].traversable);
        assertTrue(c0.terrain[1][4].traversable);
        assertFalse(c0.terrain[2][4].traversable);
        assertFalse(c0.terrain[3][4].traversable);
        assertFalse(c0.terrain[4][4].traversable);
        assertFalse(c0.terrain[5][4].traversable);
    }

    // Tests de
    //     boolean deplaceBolide(List<Vect>)
    @Test
    public void deplaceBolideLibre1() {
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(1, 0));
        boolean ok = c.deplaceBolide(deplacements);
        assertTrue(ok);
        assertEquals(new Vect(2, 3), c.bolide.position);
        assertEquals(new Vect(1, 2), c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideLibre2() {
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(1, 1));
        boolean ok = c.deplaceBolide(deplacements);
        assertTrue(ok);
        assertEquals(new Vect(2, 6), c.bolide.position);
        assertEquals(new Vect(1, 2), c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideLibre3() {
        c.bolide.position = new Vect(1, 5);
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(1, 1));
        deplacements.add(new Vect(1, 1));
        deplacements.add(new Vect(1, 1));
        boolean ok = c.deplaceBolide(deplacements);
        assertTrue(ok);
        assertEquals(new Vect(4, 8), c.bolide.position);
        assertEquals(new Vect(1, 2), c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideLibre4() {
        c.bolide.position = new Vect(1, 1);
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(0, 1));
        boolean ok = c.deplaceBolide(deplacements);
        assertTrue(ok);
        assertEquals(new Vect(1, 3), c.bolide.position);
        assertEquals(new Vect(1, 2), c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideCrash1() {
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(1, 0));
        deplacements.add(new Vect(1, 0));
        deplacements.add(new Vect(1, 0));
        boolean ok = c.deplaceBolide(deplacements);
        assertFalse(ok);
        assertEquals(new Vect(2, 3), c.bolide.position);
        assertEquals(Vect.ZERO, c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideCrash2() {
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(-1, 1));
        boolean ok = c.deplaceBolide(deplacements);
        assertFalse(ok);
        assertEquals(new Vect(1, 5), c.bolide.position);
        assertEquals(Vect.ZERO, c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideCrash3() {
        c.bolide.position = new Vect(1, 5);
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(1, 1));
        deplacements.add(new Vect(1, 0));
        deplacements.add(new Vect(0, 1));
        deplacements.add(new Vect(1, 1));
        boolean ok = c.deplaceBolide(deplacements);
        assertFalse(ok);
        assertEquals(new Vect(2, 6), c.bolide.position);
        assertEquals(Vect.ZERO, c.bolide.vitesse);
    }
    @Test
    public void deplaceBolideCrash4() {
        c.bolide.position = new Vect(1, 1);
        List<Vect> deplacements = new LinkedList<>();
        deplacements.add(new Vect(1, 0));
        deplacements.add(new Vect(1, 0));
        boolean ok = c.deplaceBolide(deplacements);
        assertFalse(ok);
        assertEquals(new Vect(1, 1), c.bolide.position);
        assertEquals(Vect.ZERO, c.bolide.vitesse);
    }

    // Tests de
    //     void gereClic(Vect)
    @Test
    public void gereClic1() {
        c.gereClic(new Vect(1, 3));
        assertEquals(new Vect(2, 5), c.bolide.position);
        assertEquals(new Vect(1, 2), c.bolide.vitesse);
    }
    @Test
    public void gereClic2() {
        c.gereClic(new Vect(1, 7));
        assertEquals(new Vect(2, 6), c.bolide.position);
        assertEquals(new Vect(1, 3), c.bolide.vitesse);
    }
    @Test
    public void gereClic3() {
        c.gereClic(new Vect(0, 3));
        assertEquals(new Vect(1, 5), c.bolide.position);
        assertEquals(new Vect(0, 2), c.bolide.vitesse);
    }
    @Test
    public void gereClic4() {
        c.gereClic(new Vect(2, 0));
        assertEquals(new Vect(2, 4), c.bolide.position);
        assertEquals(new Vect(1, 1), c.bolide.vitesse);
    }
    @Test
    public void gereClic5() {
        c.bolide.vitesse = new Vect(0, 2);
        c.gereClic(new Vect(1, 6));
        assertEquals(new Vect(1, 6), c.bolide.position);
        assertEquals(new Vect(0, 3), c.bolide.vitesse);
    }
    @Test
    public void gereClic6() {
        c.bolide.vitesse = new Vect(0, 2);
        c.gereClic(new Vect(0, 4));
        assertEquals(new Vect(1, 4), c.bolide.position);
        assertEquals(Vect.ZERO, c.bolide.vitesse);
    }

}
