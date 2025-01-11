package tomorimod.monsters.uika;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.monitors.BaseMonitor;
import tomorimod.monsters.sakishadow.SakiShadowMonster;

public class DamageNumFrozeMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {
    @Override
    public void receiveOnPlayerTurnStart() {
        UikaMonster.damageNumFroze=false;
        SakiShadowMonster.damageNumFrozen=false;
    }
}
