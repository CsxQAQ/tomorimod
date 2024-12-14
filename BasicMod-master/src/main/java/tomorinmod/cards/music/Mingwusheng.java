package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.util.CardStats;

public class Mingwusheng extends BaseMusicCard {
    public static final String ID = makeID(Mingwusheng.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Mingwusheng() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    private final static int DAMAGE_COMMON = 4;
    private final static int UPG_DAMAGE_COMMON = 2;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 2;
    private final static int UPG_MAGIC_COMMON = 1;

    private final static int DAMAGE_UNCOMMON = 5;
    private final static int UPG_DAMAGE_UNCOMMON = 3;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 3;
    private final static int UPG_MAGIC_UNCOMMON = 2;

    private final static int DAMAGE_RARE = 5;
    private final static int UPG_DAMAGE_RARE = 3;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 3;
    private final static int UPG_MAGIC_RARE = 2;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.musicRarity.equals(MusicRarity.RARE)){
            addToBot(new GainEnergyAction(2));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new Gravity(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mingwusheng();
    }

}
