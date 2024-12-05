package tomorinmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Shine;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.FormToSave;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

import java.util.Iterator;

public class WeAreMygo extends BaseCard {

    private FormToSave formToSave;

    public static final String ID = makeID(WeAreMygo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public WeAreMygo() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeAreMygoPower(p),1));

        if (AbstractDungeon.player instanceof MyCharacter) {
            MyCharacter myCharacter = (MyCharacter) AbstractDungeon.player;
            FormToSave.getInstance().getForms().add("WeAreMygoPower");
            AddTagsUtils.addTags(this, CustomTags.MOMENT);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WeAreMygo();
    }
}
