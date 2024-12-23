package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.custompowers.ConveyFeelingPower;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import java.util.ArrayList;

public class YourTenDraws extends BaseCard {

    public static final String ID = makeID(YourTenDraws.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public YourTenDraws() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<BaseCard> modCards= CustomUtils.modCardGroup;
        for(int i=0;i<10;i++){
            int randomResult = AbstractDungeon.miscRng.random(modCards.size()-1);
            while(modCards.get(randomResult).rarity==CardRarity.SPECIAL
            ||modCards.get(randomResult).rarity==CardRarity.BASIC){
                randomResult= AbstractDungeon.miscRng.random(modCards.size()-1);
            }
            BaseCard card=modCards.get(randomResult).makeStatEquivalentCopy();
            if(YourTenDraws.this.upgraded){
                card.upgrade();
            }
            addToTop(new MakeTempCardInHandAction(card, 1));
        }
    }

    private void updateDescription() {

        if(upgraded){
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new YourTenDraws();
    }
}
