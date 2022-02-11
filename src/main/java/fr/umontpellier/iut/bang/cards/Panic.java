package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Panic extends OrangeCard {

    public Panic(int value, CardSuit suit) {
        super("Panic!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        player.removeFromHand(this);
        Player choisit = player.choosePlayer("choisissez un joueur",player.getPlayersInRange(1),true);
        List<Card> bc = new ArrayList<>();


        if(choisit != null){
            bc.addAll(choisit.getInPlay());
            Card card = choisit.chooseCard(
                    "Choisisez une carte qu'il doit vous donner",
                    bc,
                    true,
                    true
            );
            if (card == null){
                player.addToHand(choisit.removeRandomCardFromHand());
            }else{
                player.addToHand(card);
                choisit.removeFromInPlay((BlueCard) card);
            }
        }
    }
}
//Made by Vincent