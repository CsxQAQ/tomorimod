package tomorinmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

@SpirePatch(
        clz = SingleRelicViewPopup.class,  // 替换为要修补的类名
        method = "close"

)
public class SingleRelicViewPopupPreClosePatch {

    @SpireInsertPatch(
            rloc=0
    )
    public static void setRelicWindowOpenedTure() {
        SingleRelicViewPopupPreOpenPatch.isRelicWindowOpened=false;
    }
}