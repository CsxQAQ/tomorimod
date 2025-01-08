package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class SingleThread extends BaseCard {
    public static final String ID = makeID(SingleThread.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=1;
    public static final int UPG_MAGIC=0;

    public SingleThread() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(!p.hasPower("Buffer")){
                    addToTop(new ApplyPowerAction(p,p,new BufferPower(p,1),1));
                }
                isDone=true;
            }
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return !p.hasPower("Buffer");
    }

    public void updateDescription() {
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
            selfRetain=true;
            this.updateDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new SingleThread();
    }

}
