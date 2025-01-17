package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tomorimod.actions.MusicLoseHPAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;
import tomorimod.util.MonsterUtils;

public class Yeyingran extends BaseMusicCard {
    public static final String ID = makeID(Yeyingran.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Yeyingran() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    public final static int DAMAGE_COMMON = 0;
    public final static int UPG_DAMAGE_COMMON = 0;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 6;
    public final static int UPG_MAGIC_COMMON = 3;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 9;
    public final static int UPG_MAGIC_UNCOMMON = 4;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 9;
    public final static int UPG_MAGIC_RARE = 4;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    calculateBaseDamage(m);
                    calculateCardDamage(m);
                    addToTop(new DamageAction(m, new MusicDamageInfo(p, damage, Yeyingran.this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                    isDone=true;
                }
            });


        }else{
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    calculateBaseDamage(m);
                    calculateCardDamage(m);
                    addToTop(new DamageAction(m, new MusicDamageInfo(p, damage, Yeyingran.this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                    isDone=true;
                }
            });
        }
    }

    public void calculateBaseDamage(AbstractMonster m){
        if(musicRarity.equals(MusicRarity.RARE)){
            baseDamage= MonsterUtils.getDebuffNum(m);
        }else{

            baseDamage=MonsterUtils.getPowerNum(m, "Vulnerable");
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yeyingran();
    }

}
