package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.util.CardStats;

public class Miluri extends BaseMusicCard {
    public static final String ID = makeID(Miluri.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Miluri() {
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
    private final static int MAGIC_COMMON = 0;
    private final static int UPG_MAGIC_COMMON = 0;

    private final static int DAMAGE_UNCOMMON = 7;
    private final static int UPG_DAMAGE_UNCOMMON = 4;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 0;
    private final static int UPG_MAGIC_UNCOMMON = 0;

    private final static int DAMAGE_RARE = 7;
    private final static int UPG_DAMAGE_RARE = 4;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 0;
    private final static int UPG_MAGIC_RARE = 0;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineAmount=0;
        if(p.getPower(makeID("Shine"))!=null){
            shineAmount = p.getPower(makeID("Shine")).amount;
        }

        if(this.musicRarity.equals(MusicRarity.RARE)){
            for (int i = 0; i < shineAmount*2 + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }else{
            for (int i = 0; i < shineAmount + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Miluri();
    }

}
