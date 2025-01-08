package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Tomotomo extends BaseMonmentCard {
    public static final String ID = makeID(Tomotomo.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC = 75;
    public static final int UPG_MAGIC = 75;

    public Tomotomo() {
        super(ID, info);

        setMagic(MAGIC,UPG_MAGIC);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.magicNumber * 2, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        int monsterNum=0;
        for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!monster.isDeadOrEscaped())            {
                monsterNum++;
            }
        }
        this.addToBot(new GainGoldAction(monsterNum*magicNumber));
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tomotomo();
    }

}
