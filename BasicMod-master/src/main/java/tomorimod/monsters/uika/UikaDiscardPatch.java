package tomorimod.monsters.uika;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import tomorimod.monsters.uika.uikacard.UikaCard;

public class UikaDiscardPatch {

    @SpirePatch(
            clz= Soul.class,
            method = "discard",
            paramtypez = {
                    AbstractCard.class, boolean.class
            }
    )
    public static class DiscardInsertPatch{
        @SpireInsertPatch(
                rloc=8
        )
        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, CardGroup ___group, @ByRef Vector2[] ___pos, @ByRef Vector2[] ___target){
            if(card instanceof UikaCard){
                ___pos[0]=new Vector2(card.current_x,card.current_y);
                ___target[0]=new Vector2(Settings.WIDTH+200.0f*Settings.scale,Settings.HEIGHT-200.0f*Settings.scale);
                ___group.removeTopCard();
            }
        }
    }

    @SpirePatch(
            clz= Soul.class,
            method = "isCarryingCard"
    )
    public static class isCarryingCardPatch{
        @SpirePrefixPatch
        public static SpireReturn<Boolean> prefix(Soul __instance){
            if(__instance.card instanceof UikaCard){
                return SpireReturn.Return(true);
            }else{
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(
            clz= Soul.class,
            method = "empower",
            paramtypez = {
                    AbstractCard.class
            }
    )
    public static class empowerPatch{
        @SpirePostfixPatch
        public static void post(Soul __instance,AbstractCard card,@ByRef Vector2 ___target[]){
            if(card instanceof UikaCard){
                ___target[0]=new Vector2(UikaMonster.DRAW_X*Settings.scale,UikaMonster.DRAW_Y*Settings.scale);
            }
        }
    }

    @SpirePatch(
            clz= Soul.class,
            method = "update"
    )
    public static class updatePatch{
        @SpireInsertPatch(
                rloc =19
        )
        public static SpireReturn<?> insert(Soul __instance){
            if(__instance.card instanceof UikaCard){
                if (__instance.isDone) {
                    if (__instance.group == null) {
                        AbstractDungeon.effectList.add(new EmpowerEffect(UikaMonster.DRAW_X*Settings.scale,UikaMonster.DRAW_Y*Settings.scale));
                        __instance.isReadyForReuse = true;
                        return SpireReturn.Return();
                    }

                    switch (__instance.group.type) {
                        case MASTER_DECK:
                            __instance.card.setAngle(0.0F);
                            __instance.card.targetDrawScale = 0.75F;
                            break;
                        case DRAW_PILE:
                            __instance.card.targetDrawScale = 0.75F;
                            __instance.card.setAngle(0.0F);
                            __instance.card.lighten(false);
                            __instance.card.clearPowers();
                            AbstractDungeon.overlayMenu.combatDeckPanel.pop();
                            break;
                        case DISCARD_PILE:
                            __instance.card.targetDrawScale = 0.75F;
                            __instance.card.setAngle(0.0F);
                            __instance.card.lighten(false);
                            __instance.card.clearPowers();
                            __instance.card.teleportToDiscardPile();
                            AbstractDungeon.overlayMenu.discardPilePanel.pop();
                        case EXHAUST_PILE:
                    }

                    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                        AbstractDungeon.player.hand.applyPowers();
                    }

                    __instance.isReadyForReuse = true;
                }
                return SpireReturn.Return();
            }else{
                return SpireReturn.Continue();
            }
        }
    }


}

