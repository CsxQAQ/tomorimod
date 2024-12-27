package tomorimod.cards.basic;

import basemod.helpers.ScreenPostProcessorManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.special.SpecialCard;
import tomorimod.character.MyCharacter;
import tomorimod.powers.custompowers.MusicCompositionPower;
import tomorimod.screens.MaterialScreenProcessor;
import tomorimod.util.CardStats;

public class MusicComposition extends BaseCard implements WithoutMaterial, SpecialCard {
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
        selfRetain=true;
        isInnate=true;
        purgeOnUse=true;
    }
    //可以不要，直接判断power
    //public static boolean isMusicCompositionUsed=false;


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.getPower(MusicCompositionPower.POWER_ID)==null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //isMusicCompositionUsed=true;
        addToBot(new ApplyPowerAction(p, p, new MusicCompositionPower(p),1));
        ScreenPostProcessorManager.addPostProcessor(MaterialScreenProcessor.getInstance());
    }

    @Override
    public void upgrade(){

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
