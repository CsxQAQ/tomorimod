package tomorinmod.monitor;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.tags.CustomTags;

import java.util.Iterator;

public class GivePowersOnBattleStartMonitor implements OnStartBattleSubscriber {

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            MyCharacter myCharacter = (MyCharacter) AbstractDungeon.player;

            // 现在可以访问 forms 属性
            Iterator<String> iterator = myCharacter.forms.iterator();

            while (iterator.hasNext()) {
                String form= iterator.next();
                if (form.equals("WeAreMygoPower")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeAreMygoPower(AbstractDungeon.player), 1));
                }
            }
        }
    }
}
