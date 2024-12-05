package tomorinmod.cards.commom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.util.CardStats;

public class Poem extends BaseCard {
    public static final String ID = makeID(Poem.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Poem() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower shine = p.getPower(Shine.POWER_ID);
        if (shine != null) {
            for (int i = 0; i < shine.amount; i++) {
                addToBot(new GainEnergyAction(1));
            }
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Poem();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.updateDescription();
    }

    private void updateDescription() {
        AbstractPower shine = AbstractDungeon.player.getPower(Shine.POWER_ID);

        if (shine==null||shine.amount<=0){
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+
                    "0"+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+
                    shine.amount+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }
}
