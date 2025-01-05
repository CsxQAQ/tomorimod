package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
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
    public final static int UPG_MAGIC=3;

    public UikaDefend() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        setBackgroundTexture(imagePath("character/specialcardback/uika_attack.png"),
                imagePath("character/specialcardback/uika_attack_p.png"));
//        setPortraitTextures(imagePath("cards/uika/"+this.getClass().getSimpleName()+".png"),
//                imagePath("cards/uika/"+this.getClass().getSimpleName()+"_p.png"));
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new GainBlockAction(uikaMonster,5));
        super.uikaUse(uikaMonster);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaDefend();
    }




}
