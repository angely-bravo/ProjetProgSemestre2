package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.WillyTheKid;

public class Bang extends OrangeCard {

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Player victime = player.choosePlayer("Sur qui voulez vous tirer ?",player.getPlayersInRange(player.getWeaponRange()),false);


        int sauvetage = victime.sauvetage(player);
        sauvetage = victime.useMissed(sauvetage);
        s:
        {
            if (sauvetage == 0) break s;
            victime.decrementHealth(1, player);
        }


        if (!player.getBangCharacter().getClass().getName().equals(WillyTheKid.class.getName())){
            if(player.getWeapon()!=null) {
                if (!player.getWeapon().getClass().getName().equals(Volcanic.class.getName())) {
                    player.setBang(false);
                }
            }
            else{
                player.setBang(false);
            }
        }


    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return player.isBang();
    }


}

//Made by Angely
