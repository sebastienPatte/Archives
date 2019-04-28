package test;
import static org.junit.Assert.*;

import org.junit.*;

import coltExpress.Direction;
import coltExpress.Train;

public class TestTrain {
	private Train t;
	private String rep;
	
	@Before
	public void setUp() throws Exception {
		this.t = new Train();
		this.rep= new String("");
	}
	
	//Deplacement Avant
	@Test
	public void testDeplacementAvantRate() {
		t.deplacement(t.BANDIT_1, Direction.AVANT);
		t.deplacement(t.BANDIT_1, Direction.AVANT);
		t.deplacement(t.BANDIT_1, Direction.AVANT);
		this.rep = t.deplacement(t.BANDIT_1, Direction.AVANT);
		assertEquals("Erreur testDeplacementAvantRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà au premier Wagon");
		assertEquals("Erreur testDeplacementAvantRate : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 0);
		assertTrue("Erreur testDeplacementAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testDeplacementAvantReussi() {
		this.rep = t.deplacement(t.BANDIT_1, Direction.AVANT);
		assertEquals("Erreur testDeplacementAvantReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" se déplace vers l'avant");
		assertEquals("Erreur testDeplacementAvantReussi : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 2);
		assertTrue("Erreur testDeplacementAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	
	//Deplacement Arriere
	@Test
	public void testDeplacementArriereRate() {
		System.out.println(this.rep);
		this.rep = t.deplacement(t.BANDIT_1, Direction.ARRIERE);
		assertEquals("Erreur testDeplacementArriereRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà au dernier Wagon");
		assertEquals("Erreur testDeplacementArriereRate : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 3);
		assertTrue("Erreur testDeplacementArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementArriereRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testDeplacementArriereReussi() {
		t.deplacement(t.BANDIT_1, Direction.AVANT);
		this.rep = t.deplacement(t.BANDIT_1, Direction.ARRIERE);
		assertEquals("Erreur testDeplacementArriereReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" se déplace vers l'arrière");
		assertEquals("Erreur testDeplacementArriereReussi : mauvaise POS du bandit", t.BANDIT_1.getPOS(), 3);
		assertTrue("Erreur testDeplacementArriereReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	
	//Deplacement Bas
	@Test
	public void testDeplacementBasRate() {
		t.deplacement(t.BANDIT_1, Direction.BAS);
		this.rep = t.deplacement(t.BANDIT_1, Direction.BAS);
		assertEquals("Erreur testDeplacementBasRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà à l'intérieur du wagon");
		assertEquals("Erreur testDeplacementBasRate : bandit au mauvais étage", t.BANDIT_1.getEtage(), 0);
		assertFalse("Erreur testDeplacementAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertTrue("Erreur testDeplacementAvantRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testDeplacementBasReussi() {
		this.rep = t.deplacement(t.BANDIT_1, Direction.BAS);
		assertEquals("Erreur testDeplacementBasReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" descend");
		assertEquals("Erreur testDeplacementBasReussi : bandit au mauvais étage", t.BANDIT_1.getEtage(), 0);
		assertFalse("Erreur testDeplacementAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertTrue("Erreur testDeplacementAvantReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
		
	}
	
	//Deplacement Haut
	@Test
	public void testDeplacementHautRate() {
		this.rep = t.deplacement(t.BANDIT_1, Direction.HAUT);
		assertEquals("Erreur testDeplacementHautRate : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" est déjà sur le toit"); 
		assertEquals("Erreur testDeplacementHautRate : bandit au mauvais étage", t.BANDIT_1.getEtage(), 1);
		assertTrue("Erreur testDeplacementHautRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementHautRate : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}
	@Test
	public void testDeplacementHautReussi() {
		t.deplacement(t.BANDIT_1, Direction.BAS);
		this.rep = t.deplacement(t.BANDIT_1, Direction.HAUT);
		assertEquals("Erreur testDeplacementHautReussi : mauvaise string renvoyée",this.rep,t.BANDIT_1.getNom()+" monte");
		assertEquals("Erreur testDeplacementHautReussi : bandit au mauvais étage", t.BANDIT_1.getEtage(), 1);
		assertTrue("Erreur testDeplacementHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditToit());
		assertFalse("Erreur testDeplacementHautReussi : mauvais attributs Wagons", t.getTabWagons()[t.BANDIT_1.getPOS()].getContientBanditInterieur());
	}

	
	
}
