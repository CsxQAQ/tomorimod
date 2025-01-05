package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class Chunriying extends BaseMusicCard implements SpecialCard {
    public static final String ID = makeID(Chunriying.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public final static int DAMAGE_COMMON = 15;
    public final static int UPG_DAMAGE_COMMON = 5;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 15;
    public final static int UPG_MAGIC_COMMON = 5;

    public final static int DAMAGE_UNCOMMON = 15;
    public final static int UPG_DAMAGE_UNCOMMON = 5;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 15;
    public final static int UPG_MAGIC_UNCOMMON = 5;

    public final static int DAMAGE_RARE = 15;
    public final static int UPG_DAMAGE_RARE = 5;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 15;
    public final static int UPG_MAGIC_RARE = 5;

    public Chunriying() {
        super(ID, info,new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));

        setBackgroundTexture(imagePath("character/cardback/bg_attack.png"),
                imagePath("character/cardback/bg_attack_p.png"));
        isMultiDamage=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new MusicDamageAllEnemiesAction(p, baseDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new HealAction(p,p, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Chunriying();
    }

}
