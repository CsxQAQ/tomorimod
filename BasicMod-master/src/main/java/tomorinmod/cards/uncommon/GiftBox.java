package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Shine;
import tomorinmod.rewards.AnonReward;
import tomorinmod.rewards.RanaReward;
import tomorinmod.rewards.SoyoReward;
import tomorinmod.rewards.TakiReward;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class GiftBox extends BaseCard {
    public static final String ID = makeID(GiftBox.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public boolean isFlipped=false;
    private int giftFrom=-1;

    public GiftBox() {
        super(ID, info);

        this.selfRetain = true;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return isFlipped;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (giftFrom){
            case 0:
                AbstractDungeon.getCurrRoom().rewards.add(new AnonReward());
                break;
            case 1:
                AbstractDungeon.getCurrRoom().rewards.add(new SoyoReward());
                break;
            case 2:
                AbstractDungeon.getCurrRoom().rewards.add(new TakiReward());
                break;
            case 3:
                AbstractDungeon.getCurrRoom().rewards.add(new RanaReward());
                break;
        }
        AddTagsUtils.addTags(this, CustomTags.MOMENT);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GiftBox();
    }

    private void updateDescription() {
        switch (giftFrom){
            case 0:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
                break;
            case 1:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
                break;
            case 2:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[2];
                break;
            case 3:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[3];
                break;
        }
        initializeDescription();
    }

    public void flipCard(){
        this.isFlipped=true;
        int randomResult = AbstractDungeon.miscRng.random(3); // 随机生成 0, 1, 或 2
        this.giftFrom=randomResult;
        updateDescription();
    }
}
