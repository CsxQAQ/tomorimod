package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.util.CardStats;
import tomorimod.util.MonsterUtils;

public class UikaLightAndShadow extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaLightAndShadow.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public UikaLightAndShadow() {
        super(ID, info);
        setMagic(LightAndShadow.MAGIC,LightAndShadow.UPG_MAGIC);
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
                int gravityNum= MonsterUtils.getPowerNum(uikaMonster,makeID("GravityPower"));
                int shineNum= MonsterUtils.getPowerNum(uikaMonster,makeID("ShinePower"));
                if(gravityNum>=shineNum){
                    addToTop(new ApplyPowerAction(uikaMonster,uikaMonster,
                            new GravityPower(uikaMonster, magicNumber),magicNumber));
                }else{
                    addToTop(new ApplyPowerAction(uikaMonster,
                            uikaMonster,new ShinePower(uikaMonster,magicNumber),magicNumber));
                }
                isDone=true;
            }
        });
        super.uikaUse(uikaMonster);
    }
}
