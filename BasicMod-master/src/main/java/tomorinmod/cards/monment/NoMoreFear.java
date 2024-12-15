package tomorinmod.cards.monment;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class NoMoreFear extends BaseMonmentCard {

    public static final String ID = makeID(NoMoreFear.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 4;

    public NoMoreFear() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        this.exhaust=true;
        CommonKeywordIconsField.useIcons.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p,this.magicNumber),this.magicNumber));
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NoMoreFear();
    }
}
