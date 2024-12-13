package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Miluri extends BaseMusicCard {
    public static final String ID = makeID(Miluri.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Miluri() {
        super(ID, info);

        this.musicUpgradeDamage=UPG_DAMAGE;
        this.setDamage(DAMAGE,UPG_DAMAGE);
    }

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineAmount=0;
        if(p.getPower(makeID("Shine"))!=null){
            shineAmount = p.getPower(makeID("Shine")).amount;
        }

        for (int i = 0; i < shineAmount + 1; i++) {
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (target != null) {
                addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Miluri();
    }

}
