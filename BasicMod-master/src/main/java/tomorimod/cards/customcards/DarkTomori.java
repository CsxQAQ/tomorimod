package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorimod.cards.BaseCard;
import tomorimod.cards.notshow.forms.BaseFormCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.util.CardStats;

public class DarkTomori extends BaseCard {

    public static final String ID = makeID(DarkTomori.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 1;

    public DarkTomori() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int colorNum=calculateMaterialColorNum();
        addToBot(new ApplyPowerAction(p,p,new RitualPower(p,colorNum*magicNumber,
                true),colorNum*magicNumber));
    }

    public void updateDescriptionWithUPG(){
        this.rawDescription=cardStrings.DESCRIPTION;
        setCustomVar("M2",calculateMaterialColorNum());
        this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        updateDescriptionWithUPG();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription=cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public int calculateMaterialColorNum(){
        int redNum=0;
        int yellowNum=0;
        int greenNum=0;
        int passNum=0;
        for(AbstractCard card: AbstractDungeon.player.hand.group){
            switch (AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card)){
                case RED:
                    redNum=1;
                    break;
                case GREEN:
                    greenNum=1;
                    break;
                case YELLOW:
                    yellowNum=1;
                    break;
                case AQUARIUMPASS:
                    passNum++;
                    break;
                default:
                    break;
            }
        }
        int res=redNum+greenNum+yellowNum+passNum;
        return Math.min(res, 3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkTomori();
    }

//    @Override
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            upgradeBaseCost(0);
//        }
//    }

}
