package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import tomorimod.actions.cardactions.LunfuyuAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;

public class Lunfuyu extends BaseMusicCard {
    public static final String ID = makeID(Lunfuyu.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Lunfuyu() {
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
    public final static int MAGIC_COMMON = 3;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 5;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 5;
    public final static int UPG_MAGIC_RARE = 0;

    public final static int MISC_COMMOM=5;
    public final static int UPG_MISC_COMMOM=3;

    public final static int MISC_UNCOMMOM=8;
    public final static int UPG_MISC_UNCOMMOM=4;

    public int miscUpgrade;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(musicRarity.equals(MusicRarity.RARE)){
            int energyNum=this.energyOnUse;
            for(int i=0;i<energyNum;i++){
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
            p.energy.use(energyNum);
            addToBot(new LunfuyuAction(this,magicNumber));
        }else {
            addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new LunfuyuAction(this, magicNumber));
        }
    }

    @Override
    public Lunfuyu makeStatEquivalentCopy(){
        BaseMusicCard card=super.makeStatEquivalentCopy();

        Lunfuyu lunfuyu=(Lunfuyu)card;
        if(this.musicRarity==MusicRarity.DEFAULT){
            initializeMisc();
        }else{
            lunfuyu.misc=this.misc;
            lunfuyu.miscUpgrade=this.miscUpgrade;
        }
        lunfuyu.baseDamage=misc;
        if(lunfuyu.musicRarity.equals(MusicRarity.RARE)){
            lunfuyu.cost=-1;
        }
        return lunfuyu;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lunfuyu();
    }

    @Override
    public void upgrade(){
        super.upgrade();
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(makeID("TomoriApotheosisPower"))) {
            upgradeMisc();
        }else{
            if (!this.upgraded) {
                upgradeMisc();
            }
        }
        baseDamage=misc;
        updateDescription();
    }

    @Override
    public void dataInfoInitialize(){
        super.dataInfoInitialize();
        initializeMisc();
        baseDamage=misc;
        updateDescription();
    }

    public void initializeMisc(){
        switch (musicRarity){
            case COMMON:
            case DEFAULT:
                misc=MISC_COMMOM;
                miscUpgrade=UPG_MISC_COMMOM;
                break;
            case UNCOMMON:
            case RARE:
                misc=MISC_UNCOMMOM;
                miscUpgrade=UPG_MISC_UNCOMMOM;
                break;
        }
    }

    public void upgradeMisc(){
        misc+=miscUpgrade;
    }

}
