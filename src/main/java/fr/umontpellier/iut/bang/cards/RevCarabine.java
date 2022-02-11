package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class RevCarabine extends WeaponCard {
    public RevCarabine(int value, CardSuit suit) {
        super("Rev. Carabine", value, suit);
    }

    @Override
    public int getRange() {
        return 4;
    }

    public void playedBy(Player player){
        super.playedBy(player);
    }
}
//Made by Angely and Vincent