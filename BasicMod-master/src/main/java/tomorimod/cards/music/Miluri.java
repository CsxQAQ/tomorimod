package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

public class Miluri extends BaseMusicCard {
    public static final String ID = makeID(Miluri.class.getSimpleName());
    public static final CardStats info = new CardStats(

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

    public final static int DAMAGE_COMMON = 10;
    public final static int UPG_DAMAGE_COMMON = 4;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 0;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 13;
    public final static int UPG_DAMAGE_UNCOMMON = 5;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 0;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 13;
    public final static int UPG_DAMAGE_RARE = 5;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;

    public final static int REPEAT_TIME_COMMON=1;
    public final static int REPEAT_TIME_RARE=2;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineAmount= PlayerUtils.getPowerNum(makeID ("ShinePower"));

        if(this.musicRarity.equals(MusicRarity.RARE)){
            for (int i = 0; i < shineAmount*REPEAT_TIME_RARE + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new MusicDamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }else{
            for (int i = 0; i < shineAmount*REPEAT_TIME_COMMON + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new MusicDamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }
    }

    @Override
    public void updateDescription(){

        switch (musicRarity) {
            case COMMON:
            case DEFAULT:
            case UNCOMMON:
                setCustomVar("RT",REPEAT_TIME_COMMON);
                this.rawDescription=cardStrings.DESCRIPTION;
                if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
                    setCustomVar("SN",PlayerUtils.getPowerNum(makeID("ShinePower"))*REPEAT_TIME_COMMON+1);
                    this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
                }
                break;
            case RARE:
                setCustomVar("RT",REPEAT_TIME_RARE);
                this.rawDescription=cardStrings.DESCRIPTION;
                //this.rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
                if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
                    setCustomVar("SN",PlayerUtils.getPowerNum(makeID("ShinePower"))*REPEAT_TIME_RARE+1);
                    this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
                }
                break;
        }
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        updateDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        return new Miluri();
    }

}
