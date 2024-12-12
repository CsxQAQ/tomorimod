package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.util.CardStats;

public class Lunfuyu extends BaseMusic {
    public static final String ID = makeID(Lunfuyu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Lunfuyu() {
        super(ID, info);

        //this.musicUpgradeDamage=UPG_DAMAGE;
        this.musicUpgradeMagicNumber=UPG_MAGIC;
        //this.setDamage(DAMAGE,UPG_DAMAGE);
        this.setMagic(MAGIC,UPG_MAGIC);
    }


    private final static int MAGIC=5;
    private final static int UPG_MAGIC=3;

    public static int hpChangeNum=0;
    public static int curHp=-1;


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, baseDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void applyPowers(){
//        if(curHp==-1){
//            curHp=AbstractDungeon.player.currentHealth;
//        }
        if(AbstractDungeon.player.currentHealth!=curHp){
            hpChangeNum=hpChangeNum+Math.abs(curHp-AbstractDungeon.player.currentHealth);
            curHp=AbstractDungeon.player.currentHealth;
        }
        baseDamage=hpChangeNum*this.magicNumber;
    }


    @Override
    public AbstractCard makeCopy() {
        return new Lunfuyu();
    }

}
