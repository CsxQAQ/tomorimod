package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.powers.custompowers.MygoTogetherPower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaDefend extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaDefend.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );


    public final static int MAGIC=5;
    public final static int UPG_MAGIC=0;

    public UikaDefend() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        this.isEthereal = true;
        setBackgroundTexture(imagePath("character/specialcardback/uika_attack.png"),
                imagePath("character/specialcardback/uika_attack_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new MygoTogetherPower(p),0));
        addToBot(new ApplyPowerAction(p, p, new GravityPower(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ShinePower(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaDefend();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

}
