package formulinf;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;
import java.util.LinkedList;

/**
   Pour compiler et exécuter en ligne de commande, après compilation de Bolide.java
   javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar BolideTest.java
   java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore BolideTest
*/

public class BolideTest {

    Vect v01 = new Vect(0, 1);
    Vect v03 = new Vect(0, 3);
    Vect v10 = new Vect(1, 0);
    Vect v11 = new Vect(1, 1);
    Vect v12 = new Vect(1, 2);
    Vect v14 = new Vect(1, 4);
    Vect v1n1 = new Vect(1, -1);
    Vect vn10 = new Vect(-1, 0);
    Vect vn11 = new Vect(-1, 1);
    Vect vn12 = new Vect(-1, 2);
    Vect vn13 = new Vect(-1, 3);
    Vect vn1n1 = new Vect(-1, -1);
    Vect v21 = new Vect(2, 1);
    Vect vn24 = new Vect(-2, 4);
    Vect v43 = new Vect(4, 3);
    Vect v52 = new Vect(5, 2);
    Vect v54 = new Vect(5, 4);
    Vect vn51 = new Vect(-5, 1);
    Vect vn5n4 = new Vect(-5, -4);
    Vect v83 = new Vect(8, 3);
    Bolide b;
    
    @Before
    public void initBolide() {
        b = new Bolide(v12);
        b.vitesse = vn13;
    }

    // Tests du constructeur
    @Test
    public void bolideZero() {
        Bolide b = new Bolide(Vect.ZERO);
        assertEquals(Vect.ZERO, b.position);
        assertEquals(Vect.ZERO, b.vitesse);
    }
    @Test
    public void bolidePos() {
        Bolide b = new Bolide(v83);
        assertEquals(v83, b.position);
        assertEquals(Vect.ZERO, b.vitesse);
    }
    @Test
    public void bolideNeg() {
        Bolide b = new Bolide(vn12);
        assertEquals(vn12, b.position);
        assertEquals(Vect.ZERO, b.vitesse);
    }

    // Tests de
    //    Vect calculeAcceleration(Vect)
    @Test
    public void calculeAccelerationMemePosition() {
        Vect v = b.calculeAcceleration(v12);
        assertEquals(Vect.ZERO, v);
    }
    @Test
    public void calculeAccelerationDroit1() {
        Vect v = b.calculeAcceleration(v14);
        assertEquals(v01, v);
    }
    @Test
    public void calculeAccelerationDroit2() {
        Vect v = b.calculeAcceleration(vn12);
        assertEquals(vn10, v);
    }
    @Test
    public void calculeAccelerationDroit3() {
        Vect v = b.calculeAcceleration(v43);
        assertEquals(v10, v);
    }
    @Test
    public void calculeAccelerationDroit4() {
        b.position = v43;
        Vect v = b.calculeAcceleration(v14);
        assertEquals(vn10, v);
    }
    @Test
    public void calculeAccelerationDiag1() {
        Vect v = b.calculeAcceleration(v21);
        assertEquals(v1n1, v);
    }
    @Test
    public void calculeAccelerationDiag2() {
        Vect v = b.calculeAcceleration(vn5n4);
        assertEquals(vn1n1, v);
    }
    @Test
    public void calculeAccelerationDiag3() {
        Vect v = b.calculeAcceleration(v54);
        assertEquals(v11, v);
    }
    @Test
    public void calculeAccelerationDiag4() {
        b.position = v43;
        Vect v = b.calculeAcceleration(v21);
        assertEquals(vn1n1, v);
    }

    // Tests de
    //     void accelereDe(Vect)
    @Test
    public void accelereDeZero() {
        b.accelereDe(Vect.ZERO);
        assertEquals(vn13, b.vitesse);
        assertEquals(v12, b.position);
    }
    @Test
    public void accelereDeNormal1() {
        b.accelereDe(v10);
        assertEquals(v03, b.vitesse);
        assertEquals(v12, b.position);
    }
    @Test
    public void accelereDeNormal2() {
        b.accelereDe(vn11);
        assertEquals(vn24, b.vitesse);
        assertEquals(v12, b.position);
    }

    // Tests de
    //     void stop()
    @Test
    public void stopSimple() {
        b.stop();
        assertEquals(Vect.ZERO, b.vitesse);
        assertEquals(v12, b.position);
    }
    @Test
    public void stopGrandeVitesse() {
        b.vitesse = new Vect(12, 15);
        b.stop();
        assertEquals(Vect.ZERO, b.vitesse);
        assertEquals(v12, b.position);
    }

    // Tests de
    //     calculeDeplacements()
    @Test
    public void calculeDeplacementsStop() {
        b.vitesse = Vect.ZERO;
        List<Vect> d = b.calculeDeplacements();
        List<Vect> listeVide = new LinkedList<>();
        assertEquals(listeVide, d);
    }
    @Test
    public void calculeDeplacements1() {
        List<Vect> d = b.calculeDeplacements();
        List<Vect> temoin = new LinkedList<>();
        temoin.add(new Vect(0, 1));
        temoin.add(new Vect(-1, 1));
        temoin.add(new Vect(0, 1));
        assertEquals(temoin, d);
    }
    @Test
    public void calculeDeplacements2() {
        b.position = v43;
        List<Vect> d = b.calculeDeplacements();
        List<Vect> temoin = new LinkedList<>();
        temoin.add(new Vect(0, 1));
        temoin.add(new Vect(-1, 1));
        temoin.add(new Vect(0, 1));
        assertEquals(temoin, d);
    }
    @Test
    public void calculeDeplacements3() {
        b.vitesse = v52;
        List<Vect> d = b.calculeDeplacements();
        List<Vect> temoin = new LinkedList<>();
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 1));
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 1));
        temoin.add(new Vect(1, 0));
        assertEquals(temoin, d);
    }
    @Test
    public void calculeDeplacements4() {
        b.vitesse = vn51;
        List<Vect> d = b.calculeDeplacements();
        List<Vect> temoin = new LinkedList<>();
        temoin.add(new Vect(-1, 0));
        temoin.add(new Vect(-1, 0));
        temoin.add(new Vect(-1, 0));
        temoin.add(new Vect(-1, 1));
        temoin.add(new Vect(-1, 0));
        assertEquals(temoin, d);
    }
    @Test
    public void calculeDeplacements5() {
        b.vitesse = v83;
        List<Vect> d = b.calculeDeplacements();
        List<Vect> temoin = new LinkedList<>();
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 1));
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 1));
        temoin.add(new Vect(1, 0));
        temoin.add(new Vect(1, 1));
        temoin.add(new Vect(1, 0));
        assertEquals(temoin, d);
    }
}
