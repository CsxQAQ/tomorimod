package tomorinmod.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorinmod.character.MyCharacter;
import tomorinmod.screens.NotebookScreen;

import static tomorinmod.BasicMod.makeID;


public class NotebookRelic extends BaseRelic {
    private static final String NAME = NotebookRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    private boolean isOpened=false;

    public NotebookRelic() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void update() {
        super.update();

        if(AbstractDungeon.CurrentScreen.NONE==AbstractDungeon.screen){
            isOpened=false;
        }

        if (this.hb.hovered) {
            if (InputHelper.justClickedRight) {

                if (!AbstractDungeon.actionManager.turnHasEnded) {
                    if (!isOpened) {
                        if (AbstractDungeon.CurrentScreen.NONE == AbstractDungeon.screen) {
                            openCustomScreen();
                            isOpened = true;
                        }
                    } else {
                        closeCustomScreen();
                        isOpened = false;
                    }
                }
            }
        }
    }

    private void openCustomScreen() {
        BaseMod.openCustomScreen(NotebookScreen.Enum.NOTEBOOK_SCREEN, "foobar", new Shiv());
    }

    private void closeCustomScreen() {
        AbstractDungeon.closeCurrentScreen();
    }
}
