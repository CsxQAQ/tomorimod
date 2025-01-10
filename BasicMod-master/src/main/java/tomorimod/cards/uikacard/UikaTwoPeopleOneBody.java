package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

public class UikaTwoPeopleOneBody extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaTwoPeopleOneBody.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=3;
    public static final int UPG_MAGIC=0;

    public UikaTwoPeopleOneBody() {
        super(ID, info);
//        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
//                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaTwoPeopleOneBody();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int shineAmount=uikaMonster.hasPower(makeID("ShinePower"))?uikaMonster.getPower(makeID("ShinePower")).amount:0;
                addToTop(new GainBlockAction(uikaMonster,shineAmount* UikaTwoPeopleOneBody.MAGIC));
                isDone=true;
            }
        });
        super.uikaUse(uikaMonster);
    }
}
