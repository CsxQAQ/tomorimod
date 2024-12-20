package tomorinmod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.RevolutionPower;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.util.CardStats;

public class Revolution extends BaseMonmentCard {


    public static final String ID = makeID(Revolution.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC=2;
    private static final int UPG_MAGIC=1;

    public static int shines=0;
    public static boolean isRevolutionUsed=false;

    public Revolution() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isRevolutionUsed=true;

        int num=HistoryCraftRecords.getInstance().historyCraftRecords.size();
        shines=shines+num*this.magicNumber;

        addToBot(new ApplyPowerAction(p, p, new RevolutionPower(p), 1));

        //SavePermanentForm.getInstance().getForms().add("RevolutionPower");
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Revolution();
    }

}
