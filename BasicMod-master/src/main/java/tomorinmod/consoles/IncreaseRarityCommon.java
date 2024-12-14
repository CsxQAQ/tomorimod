package tomorinmod.consoles;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.devcommands.hand.Hand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorinmod.cards.music.BaseMusicCard;

public class IncreaseRarityCommon extends ConsoleCommand {
    public IncreaseRarityCommon() {
        maxExtraTokens = 2;
        minExtraTokens = 1;
        requiresPlayer = true;
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int i) {
        if (tokens.length < 2) {
            DevConsole.log("Usage: music (cardid) [num]");
            return;
        }

        String cardName = tokens[1];
        AbstractCard c = CardLibrary.getCard(cardName);
        if (c == null) {
            DevConsole.log("Card not found: " + cardName);
            return;
        }

        if (!(c instanceof BaseMusicCard)) {
            DevConsole.log("Card is not a BaseMusicCard: " + cardName);
            return;
        }

        int level = 1; // 默认等级
        if (tokens.length > 2 && ConvertHelper.tryParseInt(tokens[2]) != null) {
            level = ConvertHelper.tryParseInt(tokens[2], 0);
        }

        BaseMusicCard copy = (BaseMusicCard) c.makeCopy();
        copy.setRarityByCommond(level);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copy, true));
    }
}
