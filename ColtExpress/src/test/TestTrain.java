package test;
import static org.junit.Assert.*;

import org.junit.*;

import coltExpress.Direction;
import coltExpress.Train;
import coltExpress.Bandit;

public class TestTrain {
	private Train t;
	private String rep;
	
	@Before
	public void setUp() throws Exception {
		this.t = new Train();
		this.rep= new String("");
	}
	
	//deplacementBandit Avant
	@Test
	public void testdeplacementBanditAvantRate() {
		t.tabBandits[0].deplacement( Direction.AVANT);
		t.tabBandits[0].deplacement(Direction.AVANT);
		t.tabBandits[0].deplacement( Direction.AVANT);
		this.rep = t.tabBandits[0].deplacement( Direction.AVANT);
		assertEquals("Erreur testdeplacementBanditAvantRate : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" est déjà au premier Wagon");
		assertEquals("Erreur testdeplacementBanditAvantRate : mauvaise POS du bandit", t.tabBandits[0].getPOS(), 0);
		assertTrue("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	@Test
	public void testdeplacementBanditAvantReussi() {
		this.rep = t.tabBandits[0].deplacement( Direction.AVANT);
		assertEquals("Erreur testdeplacementBanditAvantReussi : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" se déplace vers l'avant");
		assertEquals("Erreur testdeplacementBanditAvantReussi : mauvaise POS du bandit", t.tabBandits[0].getPOS(), 2);
		assertTrue("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	
	//deplacementBandit Arriere
	@Test
	public void testdeplacementBanditArriereRate() {
		System.out.println(this.rep);
		this.rep = t.tabBandits[0].deplacement( Direction.ARRIERE);
		assertEquals("Erreur testdeplacementBanditArriereRate : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" est déjà au dernier Wagon");
		assertEquals("Erreur testdeplacementBanditArriereRate : mauvaise POS du bandit", t.tabBandits[0].getPOS(), 3);
		assertTrue("Erreur testdeplacementBanditArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	@Test
	public void testdeplacementBanditArriereReussi() {
		t.tabBandits[0].deplacement( Direction.AVANT);
		this.rep = t.tabBandits[0].deplacement( Direction.ARRIERE);
		assertEquals("Erreur testdeplacementBanditArriereReussi : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" se déplace vers l'arrière");
		assertEquals("Erreur testdeplacementBanditArriereReussi : mauvaise POS du bandit", t.tabBandits[0].getPOS(), 3);
		assertTrue("Erreur testdeplacementBanditArriereReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	
	//deplacementBandit Bas
	@Test
	public void testdeplacementBanditBasRate() {
		t.tabBandits[0].deplacement( Direction.BAS);
		this.rep = t.tabBandits[0].deplacement( Direction.BAS);
		assertEquals("Erreur testdeplacementBanditBasRate : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" est déjà à l'intérieur du wagon");
		assertEquals("Erreur testdeplacementBanditBasRate : bandit au mauvais étage", t.tabBandits[0].getEtage(), false);
		assertFalse("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertTrue("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	@Test
	public void testdeplacementBanditBasReussi() {
		this.rep = t.tabBandits[0].deplacement( Direction.BAS);
		assertEquals("Erreur testdeplacementBanditBasReussi : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" descend");
		assertEquals("Erreur testdeplacementBanditBasReussi : bandit au mauvais étage", t.tabBandits[0].getEtage(), false);
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertTrue("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
		
	}
	
	//deplacementBandit Haut
	@Test
	public void testdeplacementBanditHautRate() {
		this.rep = t.tabBandits[0].deplacement( Direction.HAUT);
		assertEquals("Erreur testdeplacementBanditHautRate : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" est déjà sur le toit"); 
		assertEquals("Erreur testdeplacementBanditHautRate : bandit au mauvais étage", t.tabBandits[0].getEtage(), true);
		assertTrue("Erreur testdeplacementBanditHautRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditHautRate : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}
	@Test
	public void testdeplacementBanditHautReussi() {
		t.tabBandits[0].deplacement( Direction.BAS);
		this.rep = t.tabBandits[0].deplacement( Direction.HAUT);
		assertEquals("Erreur testdeplacementBanditHautReussi : mauvaise string renvoyée",this.rep,t.tabBandits[0].getNom()+" monte");
		assertEquals("Erreur testdeplacementBanditHautReussi : bandit au mauvais étage", t.tabBandits[0].getEtage(), true);
		assertTrue("Erreur testdeplacementBanditHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsToit()[0]);
		assertFalse("Erreur testdeplacementBanditHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.tabBandits[0].getPOS()].getBanditsInterieur()[0]);
	}

	
	
}
