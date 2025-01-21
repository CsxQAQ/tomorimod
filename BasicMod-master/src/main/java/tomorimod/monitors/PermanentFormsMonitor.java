package tomorimod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.actions.ApplyShineAction;
import tomorimod.powers.custompowers.*;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;

public class PermanentFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        applyPermanentForms();
    }

    public static void applyPermanentForms() {
        AbstractPlayer p=AbstractDungeon.player;
        for(String permantForms: PermanentFormsSaveData.getInstance().permanentForms){
            switch (permantForms){
                case "CommonAndNature":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new CommonAndNaturePower(p)));
                    break;
                case "SmallMonment":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new WholeLifePower(p)));
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
                    break;
            }
        }
    }
}
