package tomorimod.monsters.anon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

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
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        ArrayList<AbstractMonster> mons = AbstractDungeon.getMonsters().monsters;
                        AbstractMonster anonMonster = null;

                        for (AbstractMonster m : mons) {
                            if (m instanceof AnonMonster) {
                                anonMonster = m;
                                break;
                            }
                        }

                        if (anonMonster != null) {
                            mons.remove(anonMonster);
                            mons.add(anonMonster);
                        }
                        isDone=true;
                    }
                });

                addToTop(new SpawnMonsterAction(chordMonster, true));
            }
        }
        isDone = true;
    }
}