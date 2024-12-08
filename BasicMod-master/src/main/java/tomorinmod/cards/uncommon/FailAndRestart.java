package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.MutagenicStrength;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.basic.MusicComposition;
import tomorinmod.character.MyCharacter;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class FailAndRestart extends BaseCard {
    public static final String ID = makeID(FailAndRestart.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public FailAndRestart() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : p.hand.group) {
            if(card instanceof MusicComposition){
                MusicComposition musicComposition=(MusicComposition) card;
                if(musicComposition.isCardFliped && musicComposition.name.equals("失败的创作")){
                    musicComposition.initializeMusicComposition();
                }
            }
        }

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FailAndRestart();
    }

    @Override
    public void setMaterialAndLevel(){

    }

}
