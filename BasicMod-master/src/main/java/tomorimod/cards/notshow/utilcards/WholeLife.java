//package tomorimod.cards.notshow.utilcards;
//
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import tomorimod.cards.BaseCard;
//import tomorimod.cards.notshow.SpecialCard;
//import tomorimod.character.Tomori;
//import tomorimod.util.CardStats;
//import tomorimod.util.CustomUtils;
//
//public class WholeLife extends BaseCard implements SpecialCard {
//    public static final String ID = makeID(WholeLife.class.getSimpleName());
//    public static final CardStats info = new CardStats(
//            Tomori.Meta.CARD_COLOR,
//            CardType.POWER,
//            CardRarity.SPECIAL,
//            CardTarget.SELF,
//            1
//    );
//
//    public WholeLife() {
//        super(ID, info);
//        CustomUtils.setRareBanner(this);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new WholeLife();
//    }
//
//    @Override
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            upgradeBaseCost(0);
//        }
//    }
//}
