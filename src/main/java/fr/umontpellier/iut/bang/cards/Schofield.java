package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Schofield extends WeaponCard {
    public Schofield(int value, CardSuit suit) {
        super("Schofield", value, suit);
    }

    @Override
    public int getRange() {
        return 2;
    }

    public void playedBy(Player player){
        super.playedBy(player);
    }
}
//Made by Angely and Vincent