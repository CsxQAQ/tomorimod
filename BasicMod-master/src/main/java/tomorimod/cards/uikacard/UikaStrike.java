package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.basic.Strike;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaStrike extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaStrike.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public UikaStrike() {
        super(ID, info);
        this.setMagic(Strike.DAMAGE,Strike.UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaStrike();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new DamageAction(AbstractDungeon.player,
                uikaMonster.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        super.uikaUse(uikaMonster);
    }
}
