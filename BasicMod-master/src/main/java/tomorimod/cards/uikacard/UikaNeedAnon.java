package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.customcards.NeedAnon;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

public class UikaNeedAnon extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaNeedAnon.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public final int MAGIC=1;
    public final int UPG_MAGIC=1;
    public UikaNeedAnon() {
        super(ID, info);
        exhaust=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }



    @Override
    public AbstractCard makeCopy() {
        return new UikaNeedAnon();
    }


    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int gravityNum=uikaMonster.hasPower(makeID("GravityPower"))?uikaMonster.getPower(makeID("GravityPower")).amount:0;
                if(gravityNum!=0){
                    addToTop(new ApplyPowerAction(uikaMonster,uikaMonster,new GravityPower
                            (uikaMonster,gravityNum* NeedAnon.MAGIC),gravityNum*NeedAnon.MAGIC));
                }
                isDone=true;
            }
        });
        super.uikaUse(uikaMonster);
    }
}
