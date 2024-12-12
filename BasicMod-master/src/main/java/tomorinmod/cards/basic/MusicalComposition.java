package tomorinmod.cards.basic;

import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.ScreenPostProcessor;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.special.Lyric;
import tomorinmod.character.MyCharacter;
import tomorinmod.screens.MaterialScreenProcessor;
import tomorinmod.util.CardStats;

public class MusicalComposition extends BaseCard {
    public static final String ID = makeID(MusicalComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public MusicalComposition() {
        super(ID, info);
        this.cardsToPreview = new Lyric();
        this.exhaust=true;
        this.selfRetain=true;
        this.isInnate=true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Lyric(), 1));
        ScreenPostProcessor postProcessor = new MaterialScreenProcessor();
        ScreenPostProcessorManager.addPostProcessor(postProcessor);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MusicalComposition();
    }

    @Override
    public void setMaterialAndLevel(){

    }


}
