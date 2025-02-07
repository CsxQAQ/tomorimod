package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.util.CardStats;

public class AquariumPass extends BaseCard {
    public static final String ID = makeID(AquariumPass.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public AquariumPass() {
        super(ID, info);
        this.selfRetain=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            this.rarity = CardRarity.RARE;
            upgradeName();
            AbstractCardSetMaterialPatch.initializeMaterialIcon(this);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AquariumPass();
    }

}
