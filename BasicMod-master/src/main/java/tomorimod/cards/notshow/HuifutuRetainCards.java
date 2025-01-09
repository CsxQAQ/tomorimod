package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.RetainCardsPower;
import tomorimod.util.CardStats;

public class HuifutuRetainCards extends BaseCard implements SpecialCard {

    public static final String ID = makeID(HuifutuRetainCards.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public HuifutuRetainCards() {
        super(ID, info);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void onChoseThisOption() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new RetainCardsPower(AbstractDungeon.player)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new HuifutuRetainCards();
    }

}
