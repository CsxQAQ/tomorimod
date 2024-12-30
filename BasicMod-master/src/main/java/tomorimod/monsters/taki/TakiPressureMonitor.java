package tomorimod.monsters.taki;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.forms.BaseFormCard;
import tomorimod.monitors.BaseMonitor;
import tomorimod.patches.TakiLockPatch;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.savedata.customdata.FormsSaveData;

import java.util.List;

import static tomorimod.TomoriMod.makeID;

public class TakiPressureMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {
    @Override
    public void receiveOnPlayerTurnStart() {
        for(AbstractCard c:AbstractDungeon.player.hand.group){
            TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(c,false);
        }

        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(c,false);
        }

        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(c,false);
        }

        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group){
            TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(c,false);
        }

        for(AbstractCard c:AbstractDungeon.player.limbo.group){
            TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(c,false);
        }

    }
}
