package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.CalamityJanet;

public class Missed extends OrangeCard {

    public Missed(int value, CardSuit suit) {
        super("Missed!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        player.removeFromHand(this);
        if(!player.getGame().getDiscardPile().isEmpty())
        if(player.getGame().getTopOfDiscardPile().getClass().getName().equals(Bang.class.getName()) && !player.getGame().getCurrentPlayer().equals(player)){
            super.playedBy(player);
            return;
        }
        if(player.getBangCharacter().getClass().getName().equals(CalamityJanet.class.getName())){
            asBang(player);
        }
        super.playedBy(player);
    }

    private void asBang(Player player){

        if(player.getBangCharacter().getClass().getName().equals(CalamityJanet.class.getName())){
            Card b = new Bang(2,CardSuit.HEART);
            b.playedBy(player);
            player.getGame().getDiscardPile().remove(b);
        }
    }
}

//Made by Angely
