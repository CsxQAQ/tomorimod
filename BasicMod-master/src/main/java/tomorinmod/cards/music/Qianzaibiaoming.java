package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.GravityPower;
import tomorinmod.util.CardStats;

public class Qianzaibiaoming extends BaseMusicCard {
    public static final String ID = makeID(Qianzaibiaoming.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Qianzaibiaoming() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }

    private final static int DAMAGE_COMMON = 5;
    private final static int UPG_DAMAGE_COMMON = 3;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 2;
    private final static int UPG_MAGIC_COMMON = 1;

    private final static int DAMAGE_UNCOMMON = 8;
    private final static int UPG_DAMAGE_UNCOMMON = 4;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 3;
    private final static int UPG_MAGIC_UNCOMMON = 1;

    private final static int DAMAGE_RARE = 8;
    private final static int UPG_DAMAGE_RARE = 4;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 3;
    private final static int UPG_MAGIC_RARE = 1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)) {
                addToBot(new DamageAllEnemiesAction(p, damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }else{
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    @Override
    public void update(){
        super.update();
        int gravityAmount=0;
        if(AbstractDungeon.player!=null){
            if(AbstractDungeon.player.getPower(GravityPower.POWER_ID)!=null){
                gravityAmount=AbstractDungeon.player.getPower(GravityPower.POWER_ID).amount;
            }
        }
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                this.target=CardTarget.ALL_ENEMY;
                baseDamage=timesUpgraded*UPG_DAMAGE_RARE+DAMAGE_RARE+ gravityAmount*magicNumber;
            }else if(musicRarity.equals(MusicRarity.UNCOMMON)){
                baseDamage=timesUpgraded*UPG_DAMAGE_UNCOMMON+DAMAGE_UNCOMMON+ gravityAmount*magicNumber;
            }else{
                baseDamage=timesUpgraded*UPG_DAMAGE_COMMON+DAMAGE_COMMON+ gravityAmount*magicNumber;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Qianzaibiaoming();
    }

}
