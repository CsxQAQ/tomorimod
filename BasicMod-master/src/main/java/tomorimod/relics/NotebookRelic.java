package tomorimod.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorimod.character.Tomori;
import tomorimod.screens.NotebookScreen;
import tomorimod.actions.TomoriTutorialAction;

import static tomorimod.TomoriMod.makeID;


public class NotebookRelic extends BaseRelic {
    private static final String NAME = NotebookRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    public static boolean isOpened=false;

    public NotebookRelic() {
        super(ID, NAME, Tomori.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart(){
        addToBot(new TomoriTutorialAction());
    }

    @Override
    public void update() {
        super.update();

        if (this.hb.hovered) {
            if (InputHelper.justClickedRight) {

                if (!AbstractDungeon.actionManager.turnHasEnded) {
                    if (!isOpened) {
                        if (AbstractDungeon.CurrentScreen.NONE == AbstractDungeon.screen) {
                            openCustomScreen();
                            isOpened = true;
                        }
                    } else {
                        //closeCustomScreen();
                        isOpened = false;
                    }
                }
            }
        }

        if(AbstractDungeon.CurrentScreen.NONE==AbstractDungeon.screen){
            isOpened=false;
        }
    }

    private void openCustomScreen() {
        BaseMod.openCustomScreen(NotebookScreen.Enum.NOTEBOOK_SCREEN, "foobar", new Shiv());
        NotebookScreen.clearNotebookScreenCache();
        AbstractDungeon.isScreenUp = true;


        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.dynamicBanner.hide();
    }

    private void closeCustomScreen() {
        AbstractDungeon.closeCurrentScreen();

    }


}
