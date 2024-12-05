package tomorinmod.cards.commom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.util.CardStats;

public class LifelongBand extends BaseCard {
    public static final String ID = makeID(LifelongBand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;

    public LifelongBand() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower gravity = p.getPower(Gravity.POWER_ID);

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (gravity != null) {
            for (int i = 0; i < gravity.amount; i++) {
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }

        addToBot(new DrawCardAction(p, 1));
        if (gravity != null) {
            for (int i = 0; i < gravity.amount; i++) {
                addToBot(new DrawCardAction(p, 1));
            }
        }
    }

    private void updateDescription() {
        AbstractPower gravity = AbstractDungeon.player.getPower(Gravity.POWER_ID);

        if (gravity==null||gravity.amount<=0){
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+
                    "0"+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+
                    gravity.amount+
                    CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.updateDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LifelongBand();
    }
}