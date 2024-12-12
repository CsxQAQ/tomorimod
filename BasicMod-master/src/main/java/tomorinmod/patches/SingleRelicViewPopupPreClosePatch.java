package tomorinmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

@SpirePatch(
        clz = SingleRelicViewPopup.class,
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