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
		t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		assertEquals("Erreur testdeplacementBanditAvantRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà au premier Wagon");
		assertEquals("Erreur testdeplacementBanditAvantRate : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 0);
		assertTrue("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testdeplacementBanditAvantReussi() {
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		assertEquals("Erreur testdeplacementBanditAvantReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" se déplace vers l'avant");
		assertEquals("Erreur testdeplacementBanditAvantReussi : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 2);
		assertTrue("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	
	//deplacementBandit Arriere
	@Test
	public void testdeplacementBanditArriereRate() {
		System.out.println(this.rep);
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.ARRIERE);
		assertEquals("Erreur testdeplacementBanditArriereRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà au dernier Wagon");
		assertEquals("Erreur testdeplacementBanditArriereRate : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 3);
		assertTrue("Erreur testdeplacementBanditArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testdeplacementBanditArriereReussi() {
		t.deplacementBandit(t.BANDIT_1, Direction.AVANT);
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.ARRIERE);
		assertEquals("Erreur testdeplacementBanditArriereReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" se déplace vers l'arrière");
		assertEquals("Erreur testdeplacementBanditArriereReussi : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 3);
		assertTrue("Erreur testdeplacementBanditArriereReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	
	//deplacementBandit Bas
	@Test
	public void testdeplacementBanditBasRate() {
		t.deplacementBandit(t.BANDIT_1, Direction.BAS);
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.BAS);
		assertEquals("Erreur testdeplacementBanditBasRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà à l'intérieur du wagon");
		assertEquals("Erreur testdeplacementBanditBasRate : bandit au mauvais étage", t.BANDIT_1.getEtage(), false);
		assertFalse("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertTrue("Erreur testdeplacementBanditAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testdeplacementBanditBasReussi() {
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.BAS);
		assertEquals("Erreur testdeplacementBanditBasReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" descend");
		assertEquals("Erreur testdeplacementBanditBasReussi : bandit au mauvais étage", t.BANDIT_1.getEtage(), false);
		assertFalse("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertTrue("Erreur testdeplacementBanditAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
		
	}
	
	//deplacementBandit Haut
	@Test
	public void testdeplacementBanditHautRate() {
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.HAUT);
		assertEquals("Erreur testdeplacementBanditHautRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà sur le toit"); 
		assertEquals("Erreur testdeplacementBanditHautRate : bandit au mauvais étage", t.BANDIT_1.getEtage(), true);
		assertTrue("Erreur testdeplacementBanditHautRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditHautRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testdeplacementBanditHautReussi() {
		t.deplacementBandit(t.BANDIT_1, Direction.BAS);
		this.rep = t.deplacementBandit(t.BANDIT_1, Direction.HAUT);
		assertEquals("Erreur testdeplacementBanditHautReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" monte");
		assertEquals("Erreur testdeplacementBanditHautReussi : bandit au mauvais étage", t.BANDIT_1.getEtage(), true);
		assertTrue("Erreur testdeplacementBanditHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testdeplacementBanditHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}

	
	
}
