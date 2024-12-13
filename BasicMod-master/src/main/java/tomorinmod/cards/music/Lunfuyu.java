package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Lunfuyu extends BaseMusicCard {
    public static final String ID = makeID(Lunfuyu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Lunfuyu() {
        super(ID, info);

        //this.musicUpgradeDamage=UPG_DAMAGE;
        this.musicUpgradeMagicNumber=UPG_MAGIC;
        //this.setDamage(DAMAGE,UPG_DAMAGE);
        this.setMagic(MAGIC,UPG_MAGIC);
    }


    private final static int MAGIC=4;
    private final static int UPG_MAGIC=2;

    public static int hpChangeNum=0;
    public static int curHp=-1;


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));}

    @Override
    public void applyPowers(){
        if(AbstractDungeon.player.currentHealth!=curHp){
            hpChangeNum=hpChangeNum+Math.abs(curHp-AbstractDungeon.player.currentHealth);
            curHp=AbstractDungeon.player.currentHealth;
        }
        baseDamage=hpChangeNum*this.magicNumber;
        super.applyPowers();
    }


    @Override
    public AbstractCard makeCopy() {
        return new Lunfuyu();
    }

}
