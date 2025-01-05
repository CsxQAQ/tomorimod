package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.ShinePower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaLastOne extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaLastOne.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=20;
    public static final int UPG_MAGIC=0;


    public UikaLastOne() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        exhaust=true;
//        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
//                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaLastOne();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new ShinePower(uikaMonster,UikaLastOne.MAGIC),UikaLastOne.MAGIC));
        super.uikaUse(uikaMonster);
    }
}
