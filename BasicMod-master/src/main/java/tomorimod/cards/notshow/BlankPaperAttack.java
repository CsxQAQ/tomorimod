package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class BlankPaperAttack extends BaseCard implements SpecialCard {

    public static final String ID = makeID(BlankPaperAttack.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public BlankPaperAttack() {
        super(ID, info);
        //this.isLocked=true;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i=0;i<baseMagicNumber;i++){
            addToBot(new DamageAction(m, new DamageInfo(p, damage,
                    DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlankPaperAttack();
    }

}
