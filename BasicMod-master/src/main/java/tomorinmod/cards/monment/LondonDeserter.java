package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class LondonDeserter extends BaseMonmentCard {

    public static final String ID = makeID(LondonDeserter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            3
    );

    public LondonDeserter() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if(!upgraded){
                (AbstractDungeon.getCurrRoom()).smoked = true;
            }
            AbstractDungeon.player.hideHealthBar();
            AbstractDungeon.player.isEscaping = true;
            AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
            AbstractDungeon.overlayMenu.endTurnButton.disable();
            AbstractDungeon.player.escapeTimer = 2.5F;
        }
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LondonDeserter();
    }
}
