package tomorinmod.relics;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.character.MyCharacter;
import tomorinmod.screens.NotebookScreen;

import static tomorinmod.BasicMod.imagePath;
import static tomorinmod.BasicMod.makeID;


public class Notebook extends BaseRelic {
    private static final String NAME = "Notebook"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    private boolean isOpened=false;

    public Notebook() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void update() {
        super.update();

        // 检测鼠标是否悬停在遗物上
        if (this.hb.hovered) {
            // 检测是否按下右键
            if (InputHelper.justClickedRight) {

                if(!isOpened){
                    openCustomScreen();
                    isOpened=true;
                }else{
                    isOpened=false;
                    closeCustomScreen();
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
