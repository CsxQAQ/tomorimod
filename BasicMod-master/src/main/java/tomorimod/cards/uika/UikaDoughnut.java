package tomorimod.cards.uika;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaDoughnut extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaDoughnut.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public UikaDoughnut() {
        super(ID, info);
        tags.add(CardTags.HEALING);
        this.exhaust=true;
        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded){
            addToBot(new HealAction(p,p, (p.maxHealth - p.currentHealth) / 2));
        }else{
            addToBot(new HealAction(p,p, (p.maxHealth - p.currentHealth)));
        }
        addToBot(new RemoveSpecificPowerAction(p, p, GravityPower.POWER_ID));
        //CustomUtils.addTags(this, CustomTags.MOMENT);
    }

    public void updateDescription() {
        if (!upgraded) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public void upgrade(){
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new UikaDoughnut();
    }
}
