package tomorinmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

import java.util.ArrayList;

public class SingleRelicViewPopupPatch {
    public static boolean isRelicWindowOpened=false;

    @SpirePatch(
            clz = SingleRelicViewPopup.class,
            method = "close"

    )
    public static class SingleRelicViewPopupPreClosePatch {

        @SpireInsertPatch(
                rloc=0
        )
        public static void setRelicWindowOpenedTure() {
            SingleRelicViewPopupPatch.isRelicWindowOpened=false;
        }
    }

    @SpirePatch(
            clz = SingleRelicViewPopup.class,  // 替换为要修补的类名
            method = "open",
            paramtypez={
                    AbstractRelic.class,
                    ArrayList.class,
            }
    )
    public static class SingleRelicViewPopupPreOpenPatch {

        @SpireInsertPatch(
                rloc=0
        )
        public static void setRelicWindowOpenedTure() {
            isRelicWindowOpened=true;
        }
    }
}