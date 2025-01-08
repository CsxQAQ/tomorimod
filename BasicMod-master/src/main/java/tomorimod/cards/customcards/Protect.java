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

    public final int BLOCK=1;
    public final int UPG_BLOCK=1;

    public final int MAGIC=1;
    public final int UPG_MAGIC=1;

    public Protect() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public void update(){
        super.update();
        if(AbstractDungeon.player!=null){
            if(!upgraded){
                baseBlock= AbstractDungeon.player.masterDeck.size()*BLOCK;
            }else{
                baseBlock= AbstractDungeon.player.masterDeck.size()*(BLOCK+UPG_BLOCK);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Protect();
    }
}
