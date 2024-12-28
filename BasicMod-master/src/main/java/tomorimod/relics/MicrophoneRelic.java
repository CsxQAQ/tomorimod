package tomorimod.relics;

import tomorimod.character.Tomori;

import static tomorimod.TomoriMod.makeID;


public class MicrophoneRelic extends BaseRelic {
    private static final String NAME = MicrophoneRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public MicrophoneRelic() {
        super(ID, NAME, Tomori.Meta.CARD_COLOR, RARITY, SOUND);
    }


}
