package tomorinmod.cards.special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Stone extends BaseCard {
    public static final String ID = makeID(Stone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 24;
    private static final int UPG_DAMAGE = 6;

    public Stone() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Stone();
    }

    @Override
    public void setMaterialAndLevel(){
        this.material= "stone";
        this.level=3;
    }
}