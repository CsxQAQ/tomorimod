package tomorinmod.relics;

import tomorinmod.character.MyCharacter;

import static tomorinmod.BasicMod.makeID;


public class AnonsGift extends BaseRelic {
    private static final String NAME = AnonsGift.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    public AnonsGift() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public void atBattleStart(){
        super.atBattleStart();
        //改成活力

    }


}
