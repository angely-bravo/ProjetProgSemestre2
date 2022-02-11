package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PedroRamirez extends BangCharacter {

    public PedroRamirez() {
        super("Pedro Ramirez", 4);
    }

    @Override
    public void onStartTurn(Player player) {

        if(!player.getGame().getDiscardPile().isEmpty()){
            List<Card> choice = new ArrayList<>();

            choice.add(player.getGame().getDiscardPile().pop());



            Card choix = player.chooseCard("Voulez vous piocher dans la defausse ?",choice,true,true);

            if(choix == null){
                player.discard(choice.get(0));
                super.onStartTurn(player);
            }
            else{
                player.getHand().add(choix);
                player.getHand().add(player.drawCard());
            }
        }
        else{
            super.onStartTurn(player);
        }
    }
}
