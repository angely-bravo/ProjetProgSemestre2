package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class CatBalou extends OrangeCard {

    public CatBalou(int value, CardSuit suit) {
        super("Cat Balou", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        player.removeFromHand(this);
        Player choisit = player.choosePlayer("choisissez un joueur",player.getOtherPlayers(),true);
        List<Card> bc = new ArrayList<>();
        bc.addAll(choisit.getInPlay());
        if(choisit != null){
            Card card = choisit.chooseCard(
                    "Choisisez une carte qu'il doit défausser",
                    bc,
                    true,
                    true
            );
            if (card == null){
                player.discard(choisit.removeRandomCardFromHand());
            }else{
                choisit.removeFromInPlay((BlueCard) card);
            }
        }

    }
}
//Made by Vincent