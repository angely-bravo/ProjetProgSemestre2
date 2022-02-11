package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Remington extends WeaponCard {
    public Remington(int value, CardSuit suit) {
        super("Remington", value, suit);
    }

    @Override
    public int getRange() {
        return 3;
    }

    public void playedBy(Player player){
        super.playedBy(player);
    }

}
//Made by Angely and Vincent
