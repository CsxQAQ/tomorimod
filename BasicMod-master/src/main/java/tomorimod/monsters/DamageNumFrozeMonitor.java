package tomorimod.monsters;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.monitors.BaseMonitor;
import tomorimod.monsters.mutsumi.MutsumiMonster;
import tomorimod.monsters.mutsumi.MutsumiWarningUi;
import tomorimod.monsters.sakishadow.SakiShadowMonster;
import tomorimod.monsters.uika.UikaMonster;

import java.util.ArrayList;

public class DamageNumFrozeMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {

    public static ArrayList<WarningUi> warningUis=new ArrayList<>();

    @Override
    public void receiveOnPlayerTurnStart() {
        for(WarningUi ui:warningUis){
            ui.damageFrozen=false;
        }
        warningUis.clear();
//        UikaMonster.damageNumFroze=false;
//        SakiShadowMonster.damageNumFrozen=false;
//        MutsumiMonster.damageNumFrozen=false;
    }
}
