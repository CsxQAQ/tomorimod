package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.ShinePower;

import static tomorimod.TomoriMod.makeID;

@SpirePatch(
        clz= AbstractRoom.class,
        method = "endTurn"
)
public class StartOfMonsterTurnHookPatch {
    @SpirePostfixPatch
    public static void postfix(){
        for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.hasPower(makeID("ShinePower"))){
                if(monster.getPower(makeID("ShinePower"))instanceof ShinePower){
                    ((ShinePower)monster.getPower(makeID("ShinePower"))).atStartOfMonsterTurn();
                }
            }
        }
    }
}
