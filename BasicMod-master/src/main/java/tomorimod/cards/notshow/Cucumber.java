package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.mutsumi.MutsumiMonster;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class Cucumber extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(Cucumber.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public Cucumber() {
        super(ID, info);
        this.isEthereal = true;
        setBackgroundTexture(imagePath("character/specialcardback/mutsumi_cardback.png"),
                imagePath("character/specialcardback/mutsumi_cardback_p.png"));
    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }

    @Override
    public void triggerWhenDrawn(){
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()&&monster instanceof MutsumiMonster) {
                addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,
                        new StrengthPower(monster,MutsumiMonster.STRENGTHNUM),MutsumiMonster.STRENGTHNUM));
                addToBot(new HealAction(monster,AbstractDungeon.player,MutsumiMonster.HEALNUM));
            }
        }
//        if (this.isEthereal) {
//            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
//        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new Cucumber();
    }


}
