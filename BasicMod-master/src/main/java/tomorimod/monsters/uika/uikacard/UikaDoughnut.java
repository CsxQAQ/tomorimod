package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaDoughnut extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaDoughnut.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public UikaDoughnut() {
        super(ID, info);
        exhaust=true;
        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new UikaDoughnut();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new HealAction(uikaMonster,uikaMonster,uikaMonster.maxHealth/2));
        super.uikaUse(uikaMonster);
    }
}
