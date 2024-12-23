package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.actions.ApplyShineAction;
import tomorinmod.powers.custompowers.ShineWithMePower;
import tomorinmod.powers.custompowers.WeAreMygoPower;
import tomorinmod.powers.custompowers.StarDustPower;
import tomorinmod.powers.custompowers.SmallMonmentPower;
import tomorinmod.savedata.customdata.PermanentFormsSaveData;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;

public class PermanentFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        applyPermanentForms();
    }

    public static void applyPermanentForms() {
        AbstractPlayer p=AbstractDungeon.player;
        for(String permantForms: PermanentFormsSaveData.getInstance().permanentForms){
            switch (permantForms){
                case "SmallMonment":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SmallMonmentPower(p)));
                    break;
                case "StarDust":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StarDustPower(p)));
                    break;
                case "WeAreMygo":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new WeAreMygoPower(p)));
                    break;
                case "ShineWithMe":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ShineWithMePower(p)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyShineAction(SaveMusicDiscoverd.getInstance().musicDiscoveredNum));
            }
        }
    }
}
