package tomorinmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.TemporaryThornsPower;
import tomorinmod.util.CardStats;

import static tomorinmod.BasicMod.imagePath;

public class Watermelonworm extends BaseCard {

    public static final String ID = makeID(Watermelonworm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
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
        setBackgroundTexture(imagePath("character/specialcardback/music_cardback.png"),
                imagePath("character/specialcardback/music_cardback_p.png"));
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

//    @Override
//    public void setMaterialAndLevel(){
//        this.material= "watermelonworm";
//        this.level=3;
//    }
}
