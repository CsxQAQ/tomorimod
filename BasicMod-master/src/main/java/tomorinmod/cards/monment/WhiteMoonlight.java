package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.GravityPower;
import tomorinmod.util.CardStats;

public class WhiteMoonlight extends BaseMonmentCard {
    public static final String ID = makeID(WhiteMoonlight.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public WhiteMoonlight() {
        super(ID, info);
        tags.add(CardTags.HEALING);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded){
            addToBot(new HealAction(p,p, (p.maxHealth - p.currentHealth) / 2));
        }else{
            addToBot(new HealAction(p,p, (p.maxHealth - p.currentHealth)));
        }
        addToBot(new RemoveSpecificPowerAction(p, p, GravityPower.POWER_ID));
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    public void updateDescription() {
        if (!upgraded) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public void upgrade(){
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WhiteMoonlight();
    }
}
