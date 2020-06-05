package formulinf;

import static org.junit.Assert.*;
import org.junit.*;

/**
   Pour compiler et exécuter en ligne de commande, après compilation de Test.java
   javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar VectTest.java
   java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore VectTest
*/

public class VectTest {

    Vect v00 = new Vect(0, 0);
    Vect v01 = new Vect(0, 1);
    Vect v0n1 = new Vect(0, -1);
    Vect v02 = new Vect(0, 2);
    Vect v0n6 = new Vect(0, -6);
    Vect v10 = new Vect(1, 0);
    Vect v11 = new Vect(1, 1);
    Vect v1n1 = new Vect(1, -1);
    Vect v12 = new Vect(1, 2);
    Vect v13 = new Vect(1, 3);
    Vect vn10 = new Vect(-1, 0);
    Vect vn11 = new Vect(-1, 1);
    Vect vn1n1 = new Vect(-1, -1);
    Vect v20 = new Vect(2, 0);
    Vect v21 = new Vect(2, 1);
    Vect v23 = new Vect(2, 3);
    Vect v24 = new Vect(2, 4);
    Vect v25 = new Vect(2, 5);
    Vect v2n2 = new Vect(2, -2);
    Vect vn2n4 = new Vect(-2, -4);
    Vect v30 = new Vect(3, 0);
    Vect v31 = new Vect(3, 1);
    Vect v33 = new Vect(3, 3);
    Vect v3n7 = new Vect(3, -7);
    Vect vn33 = new Vect(-3, 3);
    Vect vn40 = new Vect(-4, 0);
    Vect vn5n2 = new Vect(-5, -2);

    // Tests du constructeur
    @Test
    public void vectZero() {
        Vect v = new Vect(0, 0);
        assertEquals(0, v.x);
        assertEquals(0, v.y);
    }
    @Test
    public void vectPos() {
        Vect v = new Vect(2, 0);
        assertEquals(2, v.x);
        assertEquals(0, v.y);
    }
    @Test
    public void vectNeg() {
        Vect v = new Vect(-1, -2);
        assertEquals(-1, v.x);
        assertEquals(-2, v.y);
    }
    
    // Tests de
    //     boolean egale(Vect)
    @Test
    public void nonEgaleZero() {
        boolean eg = v01.egale(Vect.ZERO);
        assertFalse(eg);
    }
    @Test
    public void zeroNonEgale() {
        boolean eg = Vect.ZERO.egale(v01);
        assertFalse(eg);
    }
    @Test
    public void egaleZero() {
        boolean eg = v00.egale(Vect.ZERO);
        assertTrue(eg);
    }
    @Test
    public void zeroEgale() {
        boolean eg = Vect.ZERO.egale(v00);
        assertTrue(eg);
    }
    @Test
    public void egaleAlias() {
        Vect v = v12;
        boolean eg = v12.egale(v);
        assertTrue(eg);
    }
    @Test
    public void egaleIdentique() {
        Vect v = new Vect(1, 2);
        boolean eg = v12.egale(v);
        assertTrue(eg);
    }
    @Test
    public void identiqueEgale() {
        Vect v = new Vect(1, 2);
        boolean eg = v.egale(v12);
        assertTrue(eg);
    }
    @Test
    public void nonEgaleTranspose() {
        boolean eg = v12.egale(v21);
        assertFalse(eg);
    }
    @Test
    public void nonEgaleDouble() {
        boolean eg = v12.egale(v24);
        assertFalse(eg);
    }
    @Test
    public void doubleNonEgale() {
        boolean eg = v24.egale(v12);
        assertFalse(eg);
    }

    // Tests de
    //     Vect add(Vect)
    @Test
    public void addZero() {
        Vect v = v12.add(v00);
        assertEquals(v12, v);
    }
    @Test
    public void zeroAdd() {
        Vect v = v00.add(v12);
        assertEquals(v12, v);
    }
    @Test
    public void addSimple1() {
        Vect v = v11.add(v12);
        assertEquals(v23, v);
    }
    @Test
    public void addSimple2() {
        Vect v = v12.add(v11);
        assertEquals(v23, v);
    }
    @Test
    public void addSimple3() {
        Vect v = v12.add(v21);
        assertEquals(v33, v);
    }
    @Test
    public void addNeg1() {
        Vect v = v12.add(v1n1);
        assertEquals(v21, v);
    }
    @Test
    public void addNeg2() {
        Vect v = v21.add(vn11);
        assertEquals(v12, v);
    }
    @Test
    public void addNeg3() {
        Vect v = v23.add(vn2n4);
        assertEquals(v0n1, v);
    }

    // Tests de
    //    Vect sub(Vect)
    @Test
    public void subZero() {
        Vect v = v12.sub(v00);
        assertEquals(v12, v);
    }
    @Test
    public void zeroSub1() {
        Vect v = v00.sub(v01);
        assertEquals(v0n1, v);
    }
    @Test
    public void zeroSub2() {
        Vect v = v00.sub(v24);
        assertEquals(vn2n4, v);
    }
    @Test
    public void subSimple1() {
        Vect v = v33.sub(v12);
        assertEquals(v21, v);
    }
    @Test
    public void subSimple2() {
        Vect v = v12.sub(v11);
        assertEquals(v01, v);
    }
    @Test
    public void subSimple3() {
        Vect v = v24.sub(v33);
        assertEquals(vn11, v);
    }
    @Test
    public void subNeg1() {
        Vect v = v21.sub(v1n1);
        assertEquals(v12, v);
    }
    @Test
    public void subNeg2() {
        Vect v = v24.sub(vn11);
        assertEquals(v33, v);
    }
    @Test
    public void subNeg3() {
        Vect v = v00.sub(vn2n4);
        assertEquals(v24, v);
    }

     // Tests de
     //    Vect normalise()
     @Test
     public void normaliseZero() {
         Vect v = v00.normalise();
         assertEquals(v00, v);
     }
     @Test
     public void normaliseNormal1() {
         Vect v = v01.normalise();
         assertEquals(v01, v);
     }
     @Test
     public void normaliseNormal2() {
         Vect v = v10.normalise();
         assertEquals(v10, v);
     }
     @Test
     public void normaliseNormal3() {
         Vect v = v0n1.normalise();
         assertEquals(v0n1, v);
     }
     @Test
     public void normaliseNormal4() {
         Vect v = v1n1.normalise();
         assertEquals(v1n1, v);
     }
     @Test
     public void normalisenNormal5() {
         Vect v = vn1n1.normalise();
         assertEquals(vn1n1, v);
     }
     @Test
     public void normaliseDroit1() {
         Vect v = v20.normalise();
         assertEquals(v10, v);
     }
     @Test
     public void normaliseDroit2() {
         Vect v = v30.normalise();
         assertEquals(v10, v);
     }
     @Test
     public void normaliseDroit3() {
         Vect v = v02.normalise();
         assertEquals(v01, v);
     }
     @Test
     public void normaliseDroit4() {
         Vect v = vn40.normalise();
         assertEquals(vn10, v);
     }
     @Test
     public void normaliseDroit5() {
         Vect v = v0n6.normalise();
         assertEquals(v0n1, v);
     }
     @Test
     public void normaliseDiag1() {
         Vect v = v33.normalise();
         assertEquals(v11, v);
     }
     @Test
     public void normaliseDiag2() {
         Vect v = v2n2.normalise();
         assertEquals(v1n1, v);
     }
     @Test
     public void normaliseDiag3() {
         Vect v = vn33.normalise();
         assertEquals(vn11, v);
     }
     @Test
     public void normaliseVersDroit1() {
         Vect v = v13.normalise();
         assertEquals(v01, v);
     }
     @Test
     public void normaliseVersDroit2() {
         Vect v = v25.normalise();
         assertEquals(v01, v);
     }
     @Test
     public void normaliseVersDroit3() {
         Vect v = v31.normalise();
         assertEquals(v10, v);
     }
     @Test
     public void normaliseVersDroit4() {
         Vect v = vn5n2.normalise();
         assertEquals(vn10, v);
     }
     @Test
     public void normaliseVersDiag1() {
         Vect v = v12.normalise();
         assertEquals(v11, v);
     }
     @Test
     public void normaliseVersDiag2() {
         Vect v = v24.normalise();
         assertEquals(v11, v);
     }
     @Test
     public void normaliseVersDiag3() {
         Vect v = vn2n4.normalise();
         assertEquals(vn1n1, v);
     }
     @Test
     public void normaliseVersDiag4() {
         Vect v = v3n7.normalise();
         assertEquals(v1n1, v);
     }
    
    
}
