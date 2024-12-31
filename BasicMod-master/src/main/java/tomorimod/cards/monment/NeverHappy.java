package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class NeverHappy extends BaseMonmentCard {
    public static final String ID = makeID(NeverHappy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public NeverHappy() {
        super(ID, info);
        this.exhaust=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    private static final int MAGIC = 99;
    private static final int UPG_MAGIC = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()&&!monster.hasPower(makeID("FriendlyMonsterPower"))) {
                    addToBot(new RemoveSpecificPowerAction(monster,p,"Artifact"));
                    addToBot(new ApplyPowerAction(monster, p, new VulnerablePower(monster, magicNumber, false), magicNumber));
                }
            }
        } else {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()&&!monster.hasPower(makeID("FriendlyMonsterPower"))) {
                    addToBot(new RemoveSpecificPowerAction(monster,p,"Artifact"));
                    addToBot(new ApplyPowerAction(monster, p, new VulnerablePower(monster, magicNumber, false), magicNumber));
                    addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, magicNumber, false), magicNumber));
                }
            }
        }
        super.use(p, m);
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
            upgradeName();
            updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NeverHappy();
    }


}
