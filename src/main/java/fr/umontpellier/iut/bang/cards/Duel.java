package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Duel extends OrangeCard {

    public Duel(int value, CardSuit suit) {
        super("Duel", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);

        Player victime = player.choosePlayer("Choisissez votre cible",player.getOtherPlayers(),false);
        boolean duel = true;

        while(duel){
            int t = victime.useBang(1);
            if(t != 0){
                victime.decrementHealth(1,player);
                duel = false;
            }
            if(duel) {
                t = player.useBang(1);
                if (t != 0) {
                    player.decrementHealth(1, null);
                    duel = false;
                }
            }
        }
    }
}
