
package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.YingsewuAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Yingsewu extends BaseMusicCard {
    public static final String ID = makeID(Yingsewu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Yingsewu() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_DAMAGE;
        this.musicUpgradeMagic=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        this.setMagic(MAGIC,UPG_MAGIC);
    }



    private final static int DAMAGE=6;
    private final static int UPG_DAMAGE=3;

    private final static int MAGIC=1;
    private final static int UPG_MAGIC=1;



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        for(int i=0;i<magicNumber;i++){
            addToBot(new YingsewuAction());
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yingsewu();
    }

}
