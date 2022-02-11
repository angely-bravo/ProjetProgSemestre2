package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardsTest {
    IOGame simpleGame;
    Deque<Card> discardPile, drawPile;
    Player p1, p2, p3, p4, p5, p6;
    List<Player> players;

    @BeforeEach
    void disableConsole() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
    }

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("p1", new Nobody(), Role.OUTLAW));
        players.add(new Player("p2", new Nobody(), Role.RENEGADE));
        players.add(new Player("p3", new Nobody(), Role.OUTLAW));
        players.add(new Player("p4", new Nobody(), Role.DEPUTY));
        players.add(new Player("p5", new Nobody(), Role.SHERIFF));

        simpleGame = new IOGame(players);
        for (Player p : simpleGame.getPlayers()) {
            p.getHand().clear();
        }
        discardPile = TestUtils.getDiscardPile(simpleGame);
        drawPile = TestUtils.getDrawPile(simpleGame);
        p1 = simpleGame.getPlayers().get(0);
        p2 = simpleGame.getPlayers().get(1);
        p3 = simpleGame.getPlayers().get(2);
        p4 = simpleGame.getPlayers().get(3);
        p5 = simpleGame.getPlayers().get(4);

    }


    @Test
    void testBang() {
        simpleGame.setInput("p2");
        Card bang = new Bang(1, CardSuit.HEART);

        p1.getHand().add(bang);
        p1.playFromHand(bang);
        assertEquals(3, p2.getHealthPoints());
    }


    @Test
    void testBangHorsPortee() {
        simpleGame.setInput("p3", "p2");
        Card bang = new Bang(1, CardSuit.HEART);

        p1.getHand().add(bang);
        p1.playFromHand(bang);
        assertEquals(3, p2.getHealthPoints());
        assertEquals(4, p3.getHealthPoints());
    }


    @Test
    void testBarrelAvecCoeur() {
        simpleGame.setInput("p2");
        Card bang = new Bang(1, CardSuit.SPADE);
        Card beer = new Beer(3, CardSuit.HEART);
        Card barrel = new Barrel(2, CardSuit.SPADE);

        p2.getHand().add(barrel);
        p2.playFromHand(barrel);
        drawPile.push(beer);
        p1.getHand().add(bang);
        p1.playFromHand(bang);
        assertEquals(4, p2.getHealthPoints());
        assertTrue(discardPile.contains(beer));
    }


    @Test
    void testBarrelPasDeCoeur() {
        simpleGame.setInput("p2");
        Card bang = new Bang(1, CardSuit.SPADE);
        Card beer = new Beer(3, CardSuit.DIAMOND);
        Card barrel = new Barrel(2, CardSuit.SPADE);

        p2.getHand().add(barrel);
        p2.playFromHand(barrel);
        drawPile.push(beer);
        p1.getHand().add(bang);
        p1.playFromHand(bang);
        assertEquals(3, p2.getHealthPoints());
        assertTrue(discardPile.contains(beer));
    }


    @Test
    void testBeer() {
        Card beer = new Beer(1, CardSuit.HEART);

        p1.getHand().add(beer);
        p1.decrementHealth(1, null);
        assertEquals(3, p1.getHealthPoints());
        p1.playFromHand(beer);
        assertEquals(4, p1.getHealthPoints());
    }


    @Test
    void testCatBalouCarteEnJeu() {
        simpleGame.setInput("p3", "Mustang");
        Card catBalou = new CatBalou(1, CardSuit.HEART);
        Card mustang = new Mustang(1, CardSuit.SPADE);

        p1.getHand().add(catBalou);
        p3.getHand().add(mustang);
        p3.playFromHand(mustang);
        assertTrue(p3.getInPlay().contains(mustang));
        p1.playFromHand(catBalou);
        assertFalse(p3.getInPlay().contains(mustang));
        assertTrue(discardPile.contains(mustang));
    }

    @Test
    void testCatBalouCarteEnMain() {
        simpleGame.setInput("p3", "");
        Card catBalou = new CatBalou(1, CardSuit.HEART);
        Card mustang = new Mustang(1, CardSuit.SPADE);

        p1.getHand().add(catBalou);
        p3.getHand().add(mustang);
        p1.playFromHand(catBalou);
        assertEquals(0, p3.getHand().size());
        assertTrue(discardPile.contains(mustang));
    }


    @Test
    void testDuel3Bang() {
        simpleGame.setInput("p3", "Bang!", "Bang!", "Bang!");
        Card duel = new Duel(2, CardSuit.SPADE);
        Card bang1 = new Bang(1, CardSuit.DIAMOND);
        Card bang2 = new Bang(1, CardSuit.DIAMOND);
        Card bang3 = new Bang(1, CardSuit.DIAMOND);

        p1.getHand().add(duel);
        p1.getHand().add(bang1);
        p3.getHand().add(bang2);
        p3.getHand().add(bang3);
        p1.playFromHand(duel);
        assertEquals(3, p1.getHealthPoints());
        assertEquals(4, p3.getHealthPoints());
        assertTrue(discardPile.contains(bang1));
        assertTrue(discardPile.contains(bang2));
        assertTrue(discardPile.contains(bang3));
    }


    @Test
    void testDuel3BangPass() {
        simpleGame.setInput("p3", "");
        Card duel = new Duel(2, CardSuit.SPADE);
        Card bang = new Bang(1, CardSuit.DIAMOND);

        p1.getHand().add(duel);
        p3.getHand().add(bang);
        p1.playFromHand(duel);
        assertEquals(4, p1.getHealthPoints());
        assertEquals(3, p3.getHealthPoints());
        assertTrue(p3.getHand().contains(bang));
        assertFalse(discardPile.contains(bang));
    }


    @Test
    void testDynamiteExplose() {
        simpleGame.setInput("", "Missed!", "Missed!");
        Card dynamite = new Dynamite(10, CardSuit.HEART);
        Card missed1 = new Missed(1, CardSuit.DIAMOND);
        Card missed2 = new Missed(1, CardSuit.DIAMOND);
        Card pique = new Beer(7, CardSuit.SPADE);

        p1.getHand().add(dynamite);
        drawPile.push(missed1);
        drawPile.push(missed2);
        drawPile.push(pique);
        p1.playFromHand(dynamite);
        p1.playTurn();
        assertEquals(1, p1.getHealthPoints());
        assertTrue(discardPile.contains(dynamite));
        assertFalse(p1.getInPlay().contains(dynamite));
    }


    @Test
    void testDynamiteNExplosePas() {
        simpleGame.setInput("");
        Card dynamite = new Dynamite(10, CardSuit.HEART);
        Card missed1 = new Missed(1, CardSuit.DIAMOND);
        Card missed2 = new Missed(1, CardSuit.DIAMOND);
        Card pique = new Beer(11, CardSuit.SPADE);

        p1.getHand().add(dynamite);
        drawPile.push(missed1);
        drawPile.push(missed2);
        drawPile.push(pique);
        p1.playFromHand(dynamite);
        p1.playTurn();
        assertEquals(4, p1.getHealthPoints());
        assertFalse(discardPile.contains(dynamite));
        assertFalse(p1.getInPlay().contains(dynamite));
        assertTrue(p2.getInPlay().contains(dynamite));
    }


    @Test
    void testGatling() {
        simpleGame.setInput("Bang!", "Missed!", "");
        Card gatling = new Gatling(1, CardSuit.HEART);
        Card bang1 = new Bang(1, CardSuit.HEART);
        Card bang2 = new Bang(1, CardSuit.HEART);
        Card missed1 = new Missed(1, CardSuit.HEART);
        Card missed2 = new Missed(1, CardSuit.HEART);

        p1.getHand().add(gatling);
        p2.getHand().add(bang1);
        p3.getHand().add(missed1);  // évite les dégâts
        p4.getHand().add(bang2);
        p5.getHand().add(missed2);  // prend des dégâts car passe sans jouer son Missed!

        p1.playFromHand(gatling);
        assertEquals(4, p1.getHealthPoints());
        assertEquals(3, p2.getHealthPoints());
        assertEquals(4, p3.getHealthPoints());
        assertEquals(3, p4.getHealthPoints());
        assertEquals(4, p5.getHealthPoints());  // rmq: le Shériff a 5 PV max
    }


    @Test
    void testGeneralStore() {
        simpleGame.setInput("Bang!", "Bang!", "Missed!");
        Card generalStore = new GeneralStore(1, CardSuit.SPADE);
        Card bang1 = new Bang(1, CardSuit.SPADE);
        Card bang2 = new Bang(1, CardSuit.SPADE);
        Card missed = new Missed(1, CardSuit.SPADE);
        Card beer1 = new Beer(1, CardSuit.SPADE);
        Card beer2 = new Beer(1, CardSuit.SPADE);

        drawPile.push(bang1);
        drawPile.push(beer1);
        drawPile.push(missed);
        drawPile.push(beer2);
        drawPile.push(bang2);
        p3.getHand().add(generalStore);
        p3.playFromHand(generalStore);
        assertEquals("Bang!", p3.getHand().get(0).getName());
        assertEquals("Bang!", p4.getHand().get(0).getName());
        assertEquals("Missed!", p5.getHand().get(0).getName());
        assertEquals("Beer", p1.getHand().get(0).getName());
        assertEquals("Beer", p2.getHand().get(0).getName());
    }


    @Test
    void testIndians() {
        simpleGame.setInput("Bang!", "Missed!", "", "Bang!");
        Card indians = new Indians(1, CardSuit.HEART);
        Card bang1 = new Bang(1, CardSuit.HEART);
        Card bang2 = new Bang(1, CardSuit.HEART);
        Card bang3 = new Bang(1, CardSuit.HEART);
        Card missed = new Missed(1, CardSuit.HEART);

        p1.getHand().add(indians);
        p2.getHand().add(bang1);    // évite les dégâts
        p3.getHand().add(missed);
        p4.getHand().add(bang2);    // n'évite pas les dégats car passe sans jouer son Bang!
        p5.getHand().add(bang3);    // évite les dégâts
        p1.playFromHand(indians);
        assertEquals(4, p1.getHealthPoints());
        assertEquals(4, p2.getHealthPoints());
        assertEquals(3, p3.getHealthPoints());
        assertEquals(3, p4.getHealthPoints());
        assertEquals(5, p5.getHealthPoints());  // rmq: le Shériff a 5 PV max
    }


    @Test
    void testJailNeSortPas() {
        simpleGame.setInput("p3", "Saloon", "");
        Card jail = new Jail(1, CardSuit.HEART);
        Card pique = new Beer(3, CardSuit.SPADE);
        Card saloon = new Saloon(1, CardSuit.HEART);

        p2.getHand().add(jail);
        p3.getHand().add(saloon);
        drawPile.push(pique);
        p2.playFromHand(jail);
        p3.playTurn();
        assertTrue(discardPile.contains(jail));
        assertTrue(discardPile.contains(pique));
        assertFalse(discardPile.contains(saloon));
        assertTrue(p3.getHand().contains(saloon));
    }


    @Test
    void testJailSortAvecCoeur() {
        simpleGame.setInput("p3", "Saloon", "");
        Card jail = new Jail(1, CardSuit.HEART);
        Card coeur = new Beer(3, CardSuit.HEART);
        Card saloon = new Saloon(1, CardSuit.HEART);

        p2.getHand().add(jail);
        p3.getHand().add(saloon);
        drawPile.push(coeur);
        p2.playFromHand(jail);
        p3.playTurn();
        assertTrue(discardPile.contains(jail));
        assertTrue(discardPile.contains(coeur));
        assertTrue(discardPile.contains(saloon));
        assertFalse(p3.getHand().contains(saloon));
    }


    @Test
    void testMissed() {
        simpleGame.setInput("p2", "Missed!");
        Card bang = new Bang(1, CardSuit.HEART);
        Card missed = new Missed(1, CardSuit.HEART);

        p1.getHand().add(bang);
        p2.getHand().add(missed);
        p1.playFromHand(bang);
        assertEquals(4, p2.getHealthPoints());
        assertTrue(discardPile.contains(missed));
    }


    @Test
    void testMustang() {
        Card mustang = new Mustang(1, CardSuit.HEART);

        p2.addToHand(mustang);
        p2.playFromHand(mustang);
        assertFalse(p1.getPlayersInRange(1).contains(p2));
        assertTrue(p2.getPlayersInRange(1).contains(p1));
    }

    @Test
    void testPanicCarteEnJeu() {
        simpleGame.setInput("p2", "Barrel");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);

        p2.getHand().add(barrel);
        p2.playFromHand(barrel);
        assertTrue(p2.getInPlay().contains(barrel));
        p1.getHand().add(panic);
        p1.playFromHand(panic);
        assertTrue(p1.getHand().contains(barrel));
    }

    @Test
    void testPanicCarteEnMain() {
        simpleGame.setInput("p2", "");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);

        p1.getHand().add(panic);
        p2.getHand().add(barrel);
        p1.playFromHand(panic);
        assertTrue(p2.getHand().isEmpty());
        assertTrue(p1.getHand().contains(barrel));
    }


    @Test
    void testRemington() {
        Card remington = new Remington(1, CardSuit.HEART);

        p1.getHand().add(remington);
        p1.playFromHand(remington);
        assertEquals(3, p1.getWeaponRange());
    }


    @Test
    void testRevCarabine() {
        Card revCarabine = new RevCarabine(1, CardSuit.HEART);

        p1.getHand().add(revCarabine);
        p1.playFromHand(revCarabine);
        assertEquals(4, p1.getWeaponRange());
    }


    @Test
    void testSchofield() {
        Card schofield = new Schofield(1, CardSuit.HEART);

        p1.getHand().add(schofield);
        p1.playFromHand(schofield);
        assertEquals(2, p1.getWeaponRange());
    }


    @Test
    void testScope() {
        Card scope = new Scope(1, CardSuit.HEART);

        p1.addToHand(scope);
        p1.playFromHand(scope);
        assertTrue(p1.getPlayersInRange(1).contains(p2));
        assertTrue(p1.getPlayersInRange(1).contains(p3));
        assertTrue(p1.getPlayersInRange(1).contains(p4));
        assertTrue(p1.getPlayersInRange(1).contains(p5));
        assertFalse(p3.getPlayersInRange(1).contains(p1));
    }

    @Test
    void testStageCoach() {
        Card stageCoach = new Stagecoach(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card mustang = new Mustang(1, CardSuit.DIAMOND);

        p1.getHand().add(stageCoach);
        drawPile.push(beer);
        drawPile.push(mustang);
        p1.playFromHand(stageCoach);
        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(beer));
        assertTrue(p1.getHand().contains(mustang));
    }


    @Test
    void testVolcanic() {
        simpleGame.setInput("Bang!", "p2", "Bang!", "p2", "Bang!", "p2", "");
        Card volcanic = new Volcanic(1, CardSuit.HEART);
        Card bang1 = new Bang(1, CardSuit.SPADE);
        Card bang2 = new Bang(1, CardSuit.SPADE);
        Card bang3 = new Bang(1, CardSuit.SPADE);

        p1.getHand().add(volcanic);
        p1.getHand().add(bang1);
        p1.getHand().add(bang2);
        p1.getHand().add(bang3);
        p1.playFromHand(volcanic);
        assertEquals(1, p1.getWeaponRange());
        p1.playTurn();
        assertEquals(1, p2.getHealthPoints());
        assertTrue(discardPile.contains(bang1));
        assertTrue(discardPile.contains(bang2));
        assertTrue(discardPile.contains(bang3));
    }


    @Test
    void testWellsFargo() {
        Card wellsFargo = new WellsFargo(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card mustang = new Mustang(1, CardSuit.DIAMOND);
        Card bang = new Bang(1, CardSuit.CLUB);

        p1.getHand().add(wellsFargo);
        drawPile.push(beer);
        drawPile.push(mustang);
        drawPile.push(bang);
        p1.playFromHand(wellsFargo);
        assertEquals(3, p1.getHand().size());
        assertTrue(p1.getHand().contains(beer));
        assertTrue(p1.getHand().contains(mustang));
        assertTrue(p1.getHand().contains(bang));
    }


    @Test
    void testWinchester() {
        Card winchester = new Winchester(1, CardSuit.HEART);

        p1.getHand().add(winchester);
        p1.playFromHand(winchester);
        assertEquals(5, p1.getWeaponRange());
    }

    @Test
    void test_cibler_joueur_plus_loin_avec_mustang_lors_panic_terrain1(){
        players.add(new Player("p6", new Nobody(), Role.OUTLAW));
        p6 = simpleGame.getPlayers().get(5);
        simpleGame.setInput("p4", ""); //Essai de selectionner p4 puis abandonne.
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card mustang = new Mustang(1, CardSuit.SPADE);

        p1.addToHand(mustang);
        p1.playFromHand(mustang);

        p4.getHand().add(barrel);
        p4.playFromHand(barrel);
        assertTrue(p4.getInPlay().contains(barrel));
        p1.getHand().add(panic);
        p1.playFromHand(panic);
        assertFalse(p1.getHand().contains(barrel));
    }



    @Test
    void testSaloon() {
        simpleGame.setInput("p2");
        Card bang = new Bang(1, CardSuit.HEART);
        Card saloon = new Saloon(1,CardSuit.HEART);

        p1.getHand().add(bang);
        p1.playFromHand(bang);
        p2.getHand().add(saloon);
        p2.playFromHand(saloon);
        assertEquals(4, p2.getHealthPoints());
        assertEquals(4, p1.getHealthPoints());
    }

    @Test
    void test_bang_gatling_mm_tour(){
        simpleGame.setInput("Bang!", "Missed!", "", "p2");
        Card gatling = new Gatling(1, CardSuit.HEART);
        Card bangi = new Bang (1, CardSuit.HEART);
        Card bang1 = new Bang(1, CardSuit.HEART);
        Card bang2 = new Bang(1, CardSuit.HEART);
        Card missed1 = new Missed(1, CardSuit.HEART);
        Card missed2 = new Missed(1, CardSuit.HEART);

        p1.getHand().add(gatling);
        p2.getHand().add(bang1);
        p3.getHand().add(missed1);
        p4.getHand().add(bang2);
        p5.getHand().add(missed2);
        p1.getHand().add(bangi);

        p1.playFromHand(gatling);
        p1.playFromHand(bangi);
        assertEquals(4, p1.getHealthPoints());
        assertEquals(2, p2.getHealthPoints());
        assertEquals(4, p3.getHealthPoints());
        assertEquals(3, p4.getHealthPoints());
        assertEquals(4, p5.getHealthPoints());
    }

    @Test
    void test_cibler_joueur_plus_loin_avec_scop_lors_panic_terrain(){
        simpleGame.setInput("p3", "Barrel");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card scope = new Scope(1, CardSuit.HEART);

        p1.addToHand(scope);
        p1.playFromHand(scope);

        p3.getHand().add(barrel);
        p3.playFromHand(barrel);
        assertTrue(p3.getInPlay().contains(barrel));
        p1.getHand().add(panic);
        p1.playFromHand(panic);
        assertTrue(p1.getHand().contains(barrel));
    }

    @Test
    void test_cibler_joueur_plus_loin_avec_scop_lors_panic_main(){
        simpleGame.setInput("p3", "");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card scope = new Scope(1, CardSuit.HEART);

        p1.addToHand(scope);
        p1.playFromHand(scope);

        p1.getHand().add(panic);
        p3.getHand().add(barrel);
        p1.playFromHand(panic);
        assertTrue(p3.getHand().isEmpty());
        assertTrue(p1.getHand().contains(barrel));

    }

    @Test
    void test_cibler_joueur_plus_loin_avec_mustang_lors_panic_main(){
        players.add(new Player("p6", new Nobody(), Role.OUTLAW));
        p6 = simpleGame.getPlayers().get(5);
        simpleGame.setInput("p4", "");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card mustang = new Mustang(1, CardSuit.SPADE);

        p1.addToHand(mustang);
        p1.playFromHand(mustang);

        p1.getHand().add(panic);
        p4.getHand().add(barrel);
        p1.playFromHand(panic);
        assertFalse(p4.getHand().isEmpty());
        assertFalse(p1.getHand().contains(barrel));
    }

    @Test
    void test_cibler_joueur_plus_loin_avec_mustang_lors_panic_terrain(){
        players.add(new Player("p6", new Nobody(), Role.OUTLAW));
        p6 = simpleGame.getPlayers().get(5);
        simpleGame.setInput("p4", "Barrel", "");
        Card panic = new Panic(1, CardSuit.HEART);
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card mustang = new Scope(1, CardSuit.SPADE);

        p1.addToHand(mustang);
        p1.playFromHand(mustang);

        p5.getHand().add(barrel);
        p5.playFromHand(barrel);
        assertTrue(p5.getInPlay().contains(barrel));
        p1.getHand().add(panic);
        p1.playFromHand(panic);
        assertFalse(p1.getHand().contains(barrel));
    }

    @Test
    void test_jouer_plusieurs_Bang_le_meme_tour_si_Duel(){
        simpleGame.setInput("Duel", "p2", "Bang!","Bang!", "Bang!", "p2", "");
        Card bang = new Bang(1,CardSuit.HEART);
        Card bang2 = new Bang(1,CardSuit.DIAMOND);
        Card bang3 = new Bang(1,CardSuit.DIAMOND);
        Card duel = new Duel(1, CardSuit.CLUB);

        p1.addToHand(bang);
        p2.addToHand(bang2);
        p1.addToHand(duel);
        p1.addToHand(bang3);

        p1.playTurn();

        assertEquals(2, p2.getHealthPoints());
    }

    @Test
    void test_jeu_du_mort_a_la_defausse(){
        Card bang1 = new Bang(1,CardSuit.HEART);
        Card bang2 = new Bang(1,CardSuit.DIAMOND);
        p2.addToHand(bang1);
        p2.addToHand(bang2);
        p2.decrementHealth(4, null);
        assertFalse(simpleGame.getDiscardPile().isEmpty());
    }

    @Test
    void test_jeu_du_mort_a_la_defausse_aucune_carte_en_mains(){
        p2.decrementHealth(4, null);
        assertTrue(simpleGame.getDiscardPile().isEmpty());
    }

    @Test
    void test_saloon_utilisable_deux_joueurs_restants(){
        p3.decrementHealth(4,null);
        p4.decrementHealth(4,null);
        p2.decrementHealth(1,null);
        p1.decrementHealth(2, null);


        Card saloon = new Saloon(1,CardSuit.HEART);
        p1.addToHand(saloon);
        p1.playFromHand(saloon);

        assertEquals(3, p1.getHealthPoints());
        assertEquals(4, p2.getHealthPoints());
    }

    @Test
    void test_saloon_soigne_pas_joueurs_morts(){
        p1.decrementHealth(4,null);
        p2.decrementHealth(2,null);

        Card saloon = new Saloon(1,CardSuit.HEART);
        p2.addToHand(saloon);
        p2.playFromHand(saloon);

        assertEquals(0, p1.getHealthPoints());
        assertEquals(3, p2.getHealthPoints());
    }

    @Test
    void test_beer_avec_tous_les_pv(){
        Card beer = new Beer(1, CardSuit.SPADE) ;
        p1.addToHand(beer);
        p1.playFromHand(beer);

        assertEquals(4, p1.getHealthPoints());
        assertFalse(p1.getHand().contains(beer));
    }

    @Test
    void test_beer_avec_deux_joueurs(){
        Card beer = new Beer(1, CardSuit.SPADE) ;
        p1.decrementHealth(1, null);
        p3.decrementHealth(4, null);
        p4.decrementHealth(4, null);
        p5.decrementHealth(5, null);

        p1.addToHand(beer);
        p1.playFromHand(beer);

        assertEquals(3, p1.getHealthPoints());
        assertFalse(p1.getHand().contains(beer));
    }

}
