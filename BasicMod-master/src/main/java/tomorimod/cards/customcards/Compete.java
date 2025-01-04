package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.CompetePower;
import tomorimod.powers.custompowers.CompeteMonsterPower;
import tomorimod.util.CardStats;

public class Compete extends BaseCard {

    public static final String ID = makeID(Compete.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public final int MAGIC=2;
    public final int UPG_MAGIC=0;

    public Compete() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if(monster.hasPower(makeID("CompetePowerMonster"))){
                addToBot(new RemoveSpecificPowerAction(monster,p,makeID("CompetePowerMonster")));
            }
        }
        addToBot(new RemoveSpecificPowerAction(p,p,makeID("CompetePower")));
        addToBot(new ApplyPowerAction(p,p,new CompetePower(p,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(m,p,new CompeteMonsterPower(m,magicNumber),magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Compete();
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            upgradeName();
            updateDescription();
            isInnate=true;
        }
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
        initializeDescription();
    }
}
