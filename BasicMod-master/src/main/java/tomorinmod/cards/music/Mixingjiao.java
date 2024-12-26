package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Mixingjiao extends BaseMusicCard {
    public static final String ID = makeID(Mixingjiao.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Mixingjiao() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    private final static int DAMAGE_COMMON = 6;
    private final static int UPG_DAMAGE_COMMON = 3;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 0;
    private final static int UPG_MAGIC_COMMON = 0;

    private final static int DAMAGE_UNCOMMON = 10;
    private final static int UPG_DAMAGE_UNCOMMON = 4;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 0;
    private final static int UPG_MAGIC_UNCOMMON = 0;

    private final static int DAMAGE_RARE = 10;
    private final static int UPG_DAMAGE_RARE = 4;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 0;
    private final static int UPG_MAGIC_RARE = 0;

    public void autoUse() {
        AbstractCard copy = makeSameInstanceOf();
        copy.purgeOnUse = true;
        copy.current_x = Settings.WIDTH + copy.hb.width + 50.0F * Settings.scale;
        copy.current_y = Settings.HEIGHT + copy.hb.height + 50.0F * Settings.scale;
        copy.target_x = Settings.WIDTH / 2.0F;
        copy.target_y = Settings.HEIGHT / 2.0F;
        addToBot(new NewQueueCardAction(copy, true, true, true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.contains(this)){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }else{
            if(this.musicRarity.equals(MusicRarity.RARE)){
                addToBot(new DamageAction(m, new DamageInfo(p, damage*3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }else{
                addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mixingjiao();
    }


}
