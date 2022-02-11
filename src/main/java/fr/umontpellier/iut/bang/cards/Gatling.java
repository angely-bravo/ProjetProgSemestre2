package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Gatling extends OrangeCard {

    public Gatling(int value, CardSuit suit) {
        super("Gatling", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);

        for (Player p : player.getOtherPlayers()) {
            int sauvetage = p.useMissed(1);

            if (sauvetage == 0) continue;
            p.decrementHealth(1, player);
        }
    }
}
