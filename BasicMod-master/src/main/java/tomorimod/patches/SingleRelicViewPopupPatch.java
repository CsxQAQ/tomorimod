package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
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

        @SpirePrefixPatch
        public static void prefix() {
            isRelicWindowOpened=false;
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

        @SpirePrefixPatch
        public static void prefix () {
            isRelicWindowOpened=true;
        }
    }
}