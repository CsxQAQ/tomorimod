package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;

public class Ruichengshan extends BaseMusicCard {
    public static final String ID = makeID(Ruichengshan.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Ruichengshan() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    public final static int DAMAGE_COMMON = 9;
    public final static int UPG_DAMAGE_COMMON = 4;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 0;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 12;
    public final static int UPG_DAMAGE_UNCOMMON = 5;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 0;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 12;
    public final static int UPG_DAMAGE_RARE = 5;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                m.damage(new MusicDamageInfo(p, damage, damageTypeForTurn));

                if (m.isDying || m.currentHealth <= 0) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    addToTop(new GainEnergyAction(2));
                    if(musicRarity.equals(MusicRarity.RARE)){
                        addToTop(new MakeTempCardInHandAction(Ruichengshan.this.makeStatEquivalentCopy(),true));
                        addToTop(new MakeTempCardInHandAction(Ruichengshan.this.makeStatEquivalentCopy(),true));
                    }
                }

                if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                isDone=true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ruichengshan();
    }


}
