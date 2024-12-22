package tomorinmod.cards.basic;

import basemod.helpers.ScreenPostProcessorManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.WithoutMaterial;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.InCompositionPower;
import tomorinmod.screens.MaterialScreenProcessor;
import tomorinmod.util.CardStats;

public class MusicComposition extends BaseCard implements WithoutMaterial {
    public static final String ID = makeID(MusicComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public MusicComposition() {
        super(ID, info);
        //this.exhaust=true;
        selfRetain=true;
        isInnate=true;
        purgeOnUse=true;
    }
    //可以不要，直接判断power
    public static boolean isMusicCompositionUsed=false;

    //public static ScreenPostProcessor postProcessor = new MaterialScreenProcessor();

    //已经在创作中应该要不能用
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.getPower(InCompositionPower.POWER_ID)==null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isMusicCompositionUsed=true;
        addToBot(new ApplyPowerAction(p, p, new InCompositionPower(p),1));
        ScreenPostProcessorManager.addPostProcessor(MaterialScreenProcessor.getInstance());
    }

    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new MusicComposition();
    }




}
