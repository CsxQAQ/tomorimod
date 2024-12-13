package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Yinakong extends BaseMusicCard {
    public static final String ID = makeID(Yinakong.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Yinakong() {
        super(ID, info);

        this.musicUpgradeDamage=UPG_DAMAGE;
        this.musicUpgradeMagicNumber=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        this.setMagic(MAGIC,UPG_MAGIC);
    }

    private static final int DAMAGE = 1;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC =2;


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yinakong();
    }

}
