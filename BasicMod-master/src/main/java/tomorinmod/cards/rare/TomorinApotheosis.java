package tomorinmod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class TomorinApotheosis extends BaseCard {

    public static boolean isTomorinApotheosisUsed=false;

    public static final String ID = makeID(TomorinApotheosis.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public TomorinApotheosis() {
        super(ID, info);

        //this.selfRetain=true;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isTomorinApotheosisUsed=true;

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TomorinApotheosis();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradesDescription();
            this.selfRetain=true;
        }
    }

    private void upgradesDescription() {
        if (upgraded) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+"， 固有 。";
        }
    }

}
