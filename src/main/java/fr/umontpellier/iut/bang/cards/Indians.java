package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Indians extends OrangeCard {

    public Indians(int value, CardSuit suit) {
        super("Indians!", value, suit);
    }

    public void playedBy(Player player){
       super.playedBy(player);
        for (Player p : player.getOtherPlayers()) {
            int sauvetage = p.useBang(1);

            if (sauvetage == 0) continue;
            p.decrementHealth(1, player);
        }
    }

}
