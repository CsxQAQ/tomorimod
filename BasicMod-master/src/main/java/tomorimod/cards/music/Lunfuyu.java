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
        exhaust=true;
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

    public final static int MISC_COMMOM=6;
    public final static int UPG_MISC_COMMOM=3;

    public final static int MISC_UNCOMMOM=9;
    public final static int UPG_MISC_UNCOMMOM=4;

    //public int miscUpgrade;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LunfuyuAction(this,magicNumber));
        if(musicRarity.equals(MusicRarity.RARE)){
            int energyNum=this.energyOnUse;
            for(int i=0;i<energyNum;i++){
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
            p.energy.use(energyNum);
        }else {
            addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public Lunfuyu makeStatEquivalentCopy(){
        BaseMusicCard card=super.makeStatEquivalentCopy();

        Lunfuyu lunfuyu=(Lunfuyu)card;
        if(this.musicRarity==MusicRarity.DEFAULT){
            lunfuyu.misc= lunfuyu.getMiscByRarity();
        }else{
            lunfuyu.misc=this.misc;
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
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(makeID("TomoriApotheosisPower"))) {
            this.upgradeDamage(damageUpgrade);
            this.upgradeBlock(blockUpgrade);
            this.upgradeMagicNumber(magicUpgrade);
            this.upgradeMisc();
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            if (!this.upgraded) {
                this.upgradeDamage(damageUpgrade);
                this.upgradeBlock(blockUpgrade);
                this.upgradeMagicNumber(magicUpgrade);
                this.upgradeMisc();
                upgradeName();
                //upgraded=true;
            }
        }
        baseDamage=misc;
        updateDescription();
    }

    @Override
    public void dataInfoInitialize(){
        super.dataInfoInitialize();
        misc=misc==0?getMiscByRarity():misc;
        baseDamage=misc;
        updateDescription();
    }

    public int getMiscByRarity(){
        int tmp=0;
        switch (musicRarity){
            case COMMON:
            case DEFAULT:
                tmp=MISC_COMMOM;
                break;
            case UNCOMMON:
            case RARE:
                tmp=MISC_UNCOMMOM;
                break;
        }
        return tmp;
    }

    public int getMiscUpgradeByRarity(){
        int tmp=0;
        switch (musicRarity){
            case COMMON:
            case DEFAULT:
                tmp=UPG_MISC_COMMOM;
                break;
            case UNCOMMON:
            case RARE:
                tmp=UPG_MISC_UNCOMMOM;
                break;
        }
        return tmp;
    }

    public void upgradeMisc(){
        misc+=getMiscUpgradeByRarity();
    }

}
