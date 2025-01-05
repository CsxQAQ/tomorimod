package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.actions.ApplyShineAction;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.makeID;

public class UikaLightAndShadow extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaLightAndShadow.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static int curAttribute=0;

    public int MAGIC=3;
    public int UPG_MAGIC=2;


    public UikaLightAndShadow() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new UikaLightAndShadow();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int gravityNum=uikaMonster.hasPower(makeID("GravityPower"))?uikaMonster.getPower(makeID("GravityPower")).amount:0;
                int shineNum=uikaMonster.hasPower(makeID("ShinePower"))?uikaMonster.getPower(makeID("ShinePower")).amount:0;
                if(gravityNum>=shineNum){
                    addToTop(new ApplyPowerAction(uikaMonster,uikaMonster,
                            new GravityPower(uikaMonster, LightAndShadow.MAGIC),LightAndShadow.MAGIC));
                }else{
                    addToTop(new ApplyPowerAction(uikaMonster,
                            uikaMonster,new ShinePower(uikaMonster,LightAndShadow.MAGIC),LightAndShadow.MAGIC));
                }
                isDone=true;
            }
        });
        super.uikaUse(uikaMonster);
    }
}
