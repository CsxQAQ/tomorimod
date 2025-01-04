package tomorimod.cards.uika;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

public class UikaNeedAnon extends BaseCard implements UikaCard, WithoutMaterial {

    public static final String ID = makeID(UikaNeedAnon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private final int MAGIC=1;
    private final int UPG_MAGIC=1;
    public UikaNeedAnon() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower gravity = AbstractDungeon.player.getPower(GravityPower.POWER_ID);
        int gravityAmount = gravity != null ? gravity.amount : 0;
        if(gravityAmount!=0){
            addToBot(new ApplyGravityAction(magicNumber*gravityAmount));
        }
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        return new UikaNeedAnon();
    }


}
