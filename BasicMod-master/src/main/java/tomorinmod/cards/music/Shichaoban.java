package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.util.CardStats;

public class Shichaoban extends BaseMusic {
    public static final String ID = makeID(Shichaoban.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Shichaoban() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_DAMAGE;
        //this.baseDamage=DAMAGE;
        //this.musicUpgradeMagicNumber=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        //this.setMagic(MAGIC,UPG_MAGIC);
    }


    private final static int DAMAGE=2;
    private final static int UPG_DAMAGE=1;

    //private final static int MAGIC=3;
    //private final static int UPG_MAGIC=2;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        baseDamage=baseDamage*2;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shichaoban();
    }
}
