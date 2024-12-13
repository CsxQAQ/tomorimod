package tomorinmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class FailAndRestart extends BaseCard {
    public static final String ID = makeID(FailAndRestart.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public FailAndRestart() {
        super(ID, info);
        this.exhaust=true;
    }

    //改为判断手牌中和弃牌堆中有没有失败的创作
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        for (AbstractCard card : p.hand.group) {
//            if(card instanceof Lyric){
//                Lyric lyric =(Lyric) card;
//                if(lyric.isCardFliped && lyric.name.equals("失败的创作")){
//                    lyric.initializeMusicComposition();
//                }
//            }
//        }
//
//        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FailAndRestart();
    }

    @Override
    public void setMaterialAndLevel(){

    }

}
