package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaLiveForever extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaLiveForever.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );


    public final static int MAGIC=5;
    public final static int UPG_MAGIC=0;

    public UikaLiveForever() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
//        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
//                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaLiveForever();
    }


    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new GravityPower(uikaMonster,UikaLiveForever.MAGIC),UikaLiveForever.MAGIC));
        super.uikaUse(uikaMonster);
    }
}
