package tomorinmod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.character.MyCharacter;

import java.util.Iterator;

import static tomorinmod.BasicMod.makeID;


public class SystemRelic extends BaseRelic {
    private static final String NAME = SystemRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.



    public SystemRelic() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public void onEquip(){
        AbstractRelic relicA = AbstractDungeon.player.getRelic(makeID("MicrophoneRelic"));
        if (relicA != null) {
            AbstractDungeon.player.loseRelic(relicA.relicId);
        }
    }
}
