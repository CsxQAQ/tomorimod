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

public class DamageNumFrozeMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {
    @Override
    public void receiveOnPlayerTurnStart() {
        UikaMonster.damageNumFroze=false;
        SakiShadowMonster.damageNumFrozen=false;
        MutsumiMonster.damageNumFrozen=false;
    }
}
