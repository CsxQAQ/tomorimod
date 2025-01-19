package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaDoughnut extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaDoughnut.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public UikaDoughnut() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaDoughnut();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        if(!upgraded){
            addToBot(new HealAction(uikaMonster,uikaMonster,uikaMonster.maxHealth/2));
        }else{
            addToBot(new HealAction(uikaMonster,uikaMonster,uikaMonster.maxHealth));
        }
        super.uikaUse(uikaMonster);
    }

    @Override
    public void updateDescription(){
        if(!upgraded){
            rawDescription=cardStrings.DESCRIPTION;
        }else{
            rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            updateDescription();
        }
    }
}
