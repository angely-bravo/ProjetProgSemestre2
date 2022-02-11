package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class JesseJones extends BangCharacter {

    public JesseJones() {
        super("Jesse Jones", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        List<Player> choix = new ArrayList<>();

        for(Player p : player.getOtherPlayers()){
            if(p.getHand().size()>0){
                choix.add(p);
            }
        }

        if(choix.size()!= player.getOtherPlayers().size()) {
            Player choisis = player.choosePlayer("Choisissez chez qui voler une carte !!",choix,true);

            if (choisis != null) {
                Card c = choisis.removeRandomCardFromHand();
                player.getHand().add(c);
                player.getHand().add(player.drawCard());

            }else{
                super.onStartTurn(player);
            }
        }else{
            super.onStartTurn(player);
        }
    }
}
