package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;


import java.util.ArrayList;
import java.util.List;

public class KitCarlson extends BangCharacter {

    public KitCarlson() {
        super("Kit Carlson", 4);
    }

    @Override
    public void onStartTurn(Player player){
        List<Card> aChoisir = new ArrayList<>();


        for(int i = 0; i<3; i++){
            aChoisir.add(player.getGame().drawCard());
        }


        Card choisie = player.chooseCard("Choisissez une carte a défausser (vous garderez les deux autres)", aChoisir, true, false);
        player.getGame().getDrawPile().push(choisie);
        aChoisir.remove(choisie);

        for(int i = 0; i<2; i++) {
            player.addToHand(aChoisir.get(i));
        }

    }
}
