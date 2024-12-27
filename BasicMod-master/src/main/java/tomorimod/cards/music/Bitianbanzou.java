package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tomorimod.util.CardStats;

public class Bitianbanzou extends BaseMusicCard {
    public static final String ID = makeID(Bitianbanzou.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Bitianbanzou() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
        this.exhaust=true;
    }


    private final static int DAMAGE_COMMON = 6;
    private final static int UPG_DAMAGE_COMMON = 3;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 3;
    private final static int UPG_MAGIC_COMMON = 2;

    private final static int DAMAGE_UNCOMMON = 9;
    private final static int UPG_DAMAGE_UNCOMMON = 4;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 5;
    private final static int UPG_MAGIC_UNCOMMON = 3;

    private final static int DAMAGE_RARE = 9;
    private final static int UPG_DAMAGE_RARE = 4;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 5;
    private final static int UPG_MAGIC_RARE = 3;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int negativeEffectsCount = 0;
                for (AbstractPower power : m.powers) {
                    if (power.type == AbstractPower.PowerType.DEBUFF) {
                        negativeEffectsCount += power.amount;
                    }
                }

                int extraDamage = negativeEffectsCount;
                switch (musicRarity){
                    case RARE:
                        baseDamage=DAMAGE_RARE+timesUpgraded*UPG_DAMAGE_RARE+extraDamage;
                        break;
                    case UNCOMMON:
                        baseDamage=DAMAGE_UNCOMMON+timesUpgraded*UPG_DAMAGE_UNCOMMON+extraDamage;
                        break;
                    case COMMON:
                        baseDamage=DAMAGE_COMMON+timesUpgraded*UPG_DAMAGE_COMMON+extraDamage;
                        break;
                }

                applyPowers();

                addToBot(new DamageAction(m,  new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)
                        , AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                isDone=true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bitianbanzou();
    }

}
