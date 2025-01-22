//package tomorimod.monsters.mutsumioperator;
//
//import basemod.interfaces.OnPlayerTurnStartSubscriber;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import tomorimod.monitors.BaseMonitor;
//
//public class TakiPressureMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {
//    @Override
//    public void receiveOnPlayerTurnStart() {
//        for(AbstractCard c:AbstractDungeon.player.hand.group){
//            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(c,false);
//        }
//
//        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
//            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(c,false);
//        }
//
//        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
//            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(c,false);
//        }
//
//        for(AbstractCard c:AbstractDungeon.player.exhaustPile.group){
//            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(c,false);
//        }
//
//        for(AbstractCard c:AbstractDungeon.player.limbo.group){
//            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(c,false);
//        }
//
//    }
//}
