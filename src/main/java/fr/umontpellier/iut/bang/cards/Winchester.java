package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Winchester extends WeaponCard {
    public Winchester(int value, CardSuit suit) {
        super("Winchester", value, suit);
    }

    @Override
    public int getRange() {
        return 5;
    }

    public void playedBy(Player player){
        super.playedBy(player);
    }
}
//Made by Angely and Vincent