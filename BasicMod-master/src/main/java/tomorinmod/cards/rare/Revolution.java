package tomorinmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.RevolutionPower;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

import java.util.Set;

public class Revolution extends BaseCard {


    public static final String ID = makeID(Revolution.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC=2;
    private static final int UPG_MAGIC=1;

    public static int shines=0;
    public static boolean isRevolutionUsed=false;

    public Revolution() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isRevolutionUsed=true;

        int num=HistoryCraftRecords.getInstance().historyCraftRecords.size();
        shines=shines+num*this.magicNumber;

        addToBot(new ApplyPowerAction(p, p, new RevolutionPower(p), 1));

        SavePermanentForm.getInstance().getForms().add("RevolutionPower");
        AddTagsUtils.addTags(this, CustomTags.MOMENT);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Revolution();
    }

}
