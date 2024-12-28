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
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MONEY = 30;
    private static final int UPG_MONEY = 15;

    public Tomotomo() {
        super(ID, info);

        setMagic(MONEY,UPG_MONEY);
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.magicNumber * 2, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        int totalGold = this.magicNumber * AbstractDungeon.getMonsters().monsters.size();
        this.addToBot(new GainGoldAction(totalGold));
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tomotomo();
    }

}
