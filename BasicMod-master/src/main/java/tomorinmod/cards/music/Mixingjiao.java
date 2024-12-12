package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Mixingjiao extends BaseMusic {
    public static final String ID = makeID(Mixingjiao.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Mixingjiao() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_DAMAGE;
        this.musicUpgradeMagicNumber=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        this.setMagic(MAGIC,UPG_MAGIC);
    }


    private final static int DAMAGE=10;
    private final static int UPG_DAMAGE=4;

    private final static int MAGIC=2;
    private final static int UPG_MAGIC=1;

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
            addToBot(new DamageAction(m, new DamageInfo(p, damage*(magicNumber+1), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mixingjiao();
    }


}
