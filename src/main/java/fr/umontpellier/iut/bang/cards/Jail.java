package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.Role;

import java.util.ArrayList;
import java.util.List;

public class Jail extends BlueCard {
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        player.removeFromHand(this);
        List<Player> aChoisir = new ArrayList<>();
        for (Player p: player.getOtherPlayers()) {
            for(Card c : p.getInPlay()) {
                if (c.getClass().getName().equals(Dynamite.class.getName())){
                    break;
                }
                if (p.getRole() != Role.SHERIFF) {
                    aChoisir.add(p);
                }
            }
        }

        Player choisis;

        choisis = player.choosePlayer("Choisissez qui va partir en prison !!!",player.getOtherPlayers(),false);
        choisis.addToInPlay(this);
    }

    public boolean preliminaryEffect(Player player){ //return false si la degaine reussis (il ne va pas en prison) true si elle echoue (il va en prison)
        Card carte = player.degaine();

        if (carte.getSuit()==CardSuit.HEART){
            player.removeFromInPlay(this);
            player.discard(carte);
            return false;
        }
        else{
            player.removeFromInPlay(this);
            player.discard(carte);
            return true;
        }


    }
}
//Made by Angely
