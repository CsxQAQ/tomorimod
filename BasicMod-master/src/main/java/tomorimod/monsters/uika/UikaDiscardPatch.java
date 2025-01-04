package tomorimod.monsters.uika;

import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import tomorimod.cards.uika.UikaCard;

public class UikaDiscardPatch {

//    @SpirePatch(
//            clz= Soul.class,
//            method = "shuffle",
//            paramtypez = {
//                    AbstractCard.class, boolean.class
//            }
//    )
//    public static class ShuffleInsertPatch{
//        @SpireInsertPatch(
//                rloc=7
//        )
//        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, @ByRef Vector2[] ___pos,@ByRef Vector2[] ___target){
//            if(card instanceof UikaCard){
//                ___pos[0]=new Vector2(Settings.WIDTH+200.0f*Settings.scale,Settings.HEIGHT-200.0f*Settings.scale);
//                ___target[0]=new Vector2(UikaMonster.DRAW_X,UikaMonster.DRAW_Y+400.0f* Settings.scale);
//                //AbstractDungeon.player.drawPile.group.remove(0);
//            }
//        }
//    }

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
        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, @ByRef Vector2[] ___pos,@ByRef Vector2[] ___target){
            if(card instanceof UikaCard){
                ___pos[0]=new Vector2(card.current_x,card.current_y);
                ___target[0]=new Vector2(Settings.WIDTH+200.0f*Settings.scale,Settings.HEIGHT-200.0f*Settings.scale);
            }
        }
    }
}



//                c.current_x = CardGroup.DRAW_PILE_X;
//                        c.current_y = CardGroup.DRAW_PILE_Y;
//                        c.setAngle(0.0F, true);
//                        c.lighten(false);
//                        c.drawScale = 0.12F;
//                        c.targetDrawScale = 0.75F;
