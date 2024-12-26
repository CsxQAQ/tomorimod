package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.cardactions.YinakongAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Yinakong extends BaseMusicCard {
    public static final String ID = makeID(Yinakong.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Yinakong() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    private final static int DAMAGE_COMMON = 1;
    private final static int UPG_DAMAGE_COMMON = 1;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 4;
    private final static int UPG_MAGIC_COMMON = 2;

    private final static int DAMAGE_UNCOMMON = 2;
    private final static int UPG_DAMAGE_UNCOMMON = 1;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 6;
    private final static int UPG_MAGIC_UNCOMMON = 3;

    private final static int DAMAGE_RARE = 2;
    private final static int UPG_DAMAGE_RARE = 1;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 6;
    private final static int UPG_MAGIC_RARE = 3;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                for(int i=0;i<magicNumber;i++){
                    addToBot(new YinakongAction(damage,m,this));
                }
            }else{
                for (int i = 0; i < this.magicNumber; i++) {
                    addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yinakong();
    }

}
