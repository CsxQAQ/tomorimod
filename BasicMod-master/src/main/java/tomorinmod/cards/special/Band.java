package tomorinmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Band extends BaseCard {
    public static final String ID = makeID(Band.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MULTIBLOCK = 8;
    private static final int UPG_MULTIBLOCK = 4;

    //private int isSkillCardUsed=0;

    public Band() {
        super(ID, info);
        setMagic(MULTIBLOCK, UPG_MULTIBLOCK);
    }

//    @Override
//    public void applyPowers() {
//        super.applyPowers();
//        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
//                .anyMatch(card -> card.type == CardType.SKILL)) {  // 判断本回合内是否使用过技能牌
//            isSkillCardUsed=1;
//        } else {
//            isSkillCardUsed=0;
//        }
//        updateDescription();
//    }

//    private void updateDescription() {
//        // 更新卡牌描述，考虑到增强效果
//        if (isSkillCardUsed==1) {  // 判断本回合内是否使用过技能牌
//            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION +
//                    " NL (已获得增强)";
//        } else {
//            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
//        }
//        initializeDescription();
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        if (isSkillCardUsed==1) {  // 判断本回合内是否使用过技能牌
//            this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber + 4), this.magicNumber + 4));
//        } else {
//            this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
//        }
        this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Band();
    }

    @Override
    public void setMaterialAndLevel(){
        this.material= "Band";
        this.level=3;
    }
}
