package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.cardactions.BlackListYouAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class BlacklistYou extends BaseMonmentCard {
    public static final String ID = makeID(BlacklistYou.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK, //不知道会不会出问题
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public BlacklistYou() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded){
            addToBot(new BlackListYouAction(m));
        }else{
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()) {
                    addToBot(new BlackListYouAction(monster));
                    addToBot(new WaitAction(0.2f));
                }
            }
        }
        super.use(p,m);
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            name="我要拉黑你们";
            upgradeName();
            updateDescription();
            target=CardTarget.ALL_ENEMY;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlacklistYou();
    }


}
