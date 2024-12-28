package tomorimod.monsters.anon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CallChordAction extends AbstractGameAction {

    public CallChordAction() {
    }

    public void update() {
        if (AnonMonster.chordNum <= 2) {
            int rand = AbstractDungeon.miscRng.random(0, 2);

            ChordMonster chordMonster =new ChordMonster(0.0F, 0.0F);

            chordMonster.setChordName(rand);
            for(int i=0;i<3;i++){
                if(AnonMonster.chordPos.get(i)==0){
                    chordMonster.setDrawPosition(i);
                    break;
                }
            }

            if (chordMonster != null) {
                addToBot(new SpawnMonsterAction(chordMonster, false));
            }
        }
        isDone = true;
    }
}