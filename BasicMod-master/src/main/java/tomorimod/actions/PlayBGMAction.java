package tomorimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import tomorimod.monsters.BaseMonster;
import tomorimod.patches.MusicPatch;

public class PlayBGMAction extends AbstractGameAction {
    private MusicPatch.MusicHelper musicHelper;
    private boolean isForce;
    private BaseMonster monster;

    public PlayBGMAction(MusicPatch.MusicHelper musicHelper, BaseMonster monster, boolean isForce) {
        this.musicHelper = musicHelper;
        this.isForce = isForce;
        this.monster = monster;
    }

    public PlayBGMAction(MusicPatch.MusicHelper musicHelper, BaseMonster monster) {
        this(musicHelper, monster, false);
    }

//    public void update() {
//        if (this.isForce) {
//            CardCrawlGame.music.silenceBGMInstantly();
//            CardCrawlGame.music.silenceTempBgmInstantly();
//            CardCrawlGame.music.playTempBgmInstantly(this.musicHelper.name(), true);
//            this.isDone = true;
//            return;
//        }
//        if (!this.monster.isPlayBGM) {
//            CardCrawlGame.music.silenceBGMInstantly();
//            CardCrawlGame.music.silenceTempBgmInstantly();
//            CardCrawlGame.music.playTempBgmInstantly(this.musicHelper.name(), true);
//            this.monster.isPlayBGM = true;
//        }
//
//        this.isDone = true;
//    }
    public void update(){
        CardCrawlGame.music.silenceBGMInstantly();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.playTempBgmInstantly(this.musicHelper.name(), true);
        isDone=true;
    }
}