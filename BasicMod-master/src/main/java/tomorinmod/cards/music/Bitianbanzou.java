package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.BitianbanzouAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Bitianbanzou extends BaseMusicCard {
    public static final String ID = makeID(Bitianbanzou.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Bitianbanzou() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_DAMAGE;
        //this.musicUpgradeMagicNumber=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        //this.setMagic(MAGIC,UPG_MAGIC);
        this.exhaust=true;
    }


    private final static int DAMAGE=10;
    private final static int UPG_DAMAGE=5;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new BitianbanzouAction(uuid));
    }


    @Override
    public void applyPowers(){
        baseDamage=misc+UPG_DAMAGE*timesUpgraded+DAMAGE;
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bitianbanzou();
    }

}
