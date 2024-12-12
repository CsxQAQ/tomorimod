package tomorinmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

import java.util.ArrayList;

@SpirePatch(
        clz = SingleRelicViewPopup.class,  // 替换为要修补的类名
        method = "open",
        paramtypez={
                AbstractRelic.class,
                ArrayList.class,
        }
)
public class SingleRelicViewPopupPreOpenPatch {

    public static boolean isRelicWindowOpened=false;

    @SpireInsertPatch(
            rloc=0
    )
    public static void setRelicWindowOpenedTure() {
        isRelicWindowOpened=true;
    }
}