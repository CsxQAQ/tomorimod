package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.DarkTomorinPower;
import tomorinmod.powers.TemporaryThornsPower;
import tomorinmod.savedata.SaveForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class Watermelonworm extends BaseCard {

    public static final String ID = makeID(Watermelonworm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 20;
    private static final int UPG_BLOCK = 6;
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 4;

    public Watermelonworm() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 增加 20 点护甲
        this.addToBot(new GainBlockAction(p, p, this.block));

        // 增加 6 点临时荆棘
        this.addToBot(new ApplyPowerAction(p, p, new TemporaryThornsPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Watermelonworm();
    }

    @Override
    public void setMaterialAndLevel(){
        this.material= "Watermelonworm";
        this.level=3;
    }
}
