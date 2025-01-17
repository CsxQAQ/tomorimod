package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Protect extends BaseCard {

    public static final String ID = makeID(Protect.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    //public final int BLOCK=1;
    //public final int UPG_BLOCK=1;

    public final int MAGIC=1;
    public final int UPG_MAGIC=1;

    public Protect() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyPowers();
        addToBot(new GainBlockAction(p,block));
    }

    public void updateDescriptionWithUPG(){
        this.rawDescription=cardStrings.DESCRIPTION;
        this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        calculateBaseBlock();
        super.applyPowers();
        updateDescriptionWithUPG();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription=cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateBaseBlock(){
        baseBlock= AbstractDungeon.player.masterDeck.size()*magicNumber;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Protect();
    }
}
