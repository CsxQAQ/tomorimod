package tomorinmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.music.Chunriying;
import tomorinmod.character.MyCharacter;
import tomorinmod.savedata.SaveMusicDiscoverd;
import tomorinmod.util.CardStats;

public class MemoryInCrychic extends BaseCard {

    public static final String ID = makeID(MemoryInCrychic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public MemoryInCrychic() {
        super(ID, info);
        this.cardsToPreview = new Chunriying();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(SaveMusicDiscoverd.getInstance().musicDiscovered.contains("chunriying")){
            Chunriying.isIntensify=true;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MemoryInCrychic();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
