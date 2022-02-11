package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;


public class Dynamite extends BlueCard {

    public Dynamite(int value, CardSuit suit) {
        super("Dynamite", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        player.removeFromHand(this);

    }

    public void preliminaryEffect(Player player){

        Card carte = player.degaine();

        if (carte.getSuit()==CardSuit.SPADE && 2<carte.getValue()&& carte.getValue()<9){
            player.removeFromInPlay(this);
            player.decrementHealth(3,null);

        }
        else{
            player.getGame().getPlayers().get((player.getGame().getPlayers().indexOf(player)+1)%player.getGame().getPlayers().size()).addToInPlay(this);
            player.getInPlay().remove(this);
        }
    }
}
//Made by Angely
