package tomorimod.monsters.mutsumioperator;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import tomorimod.monsters.BaseMonster;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SakiMachineMonster extends BaseMonster {
    public static final String ID = makeID(SakiMachineMonster.class.getSimpleName());
    private static final MonsterStrings monsterStrings =
            CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public static final int HP_MIN         = 200;
    public static final int HP_MAX         = 200;
    public static final int DAMAGE_0       = 8;
    public static final int DAMAGE_1       = 12;
    public static final int DAMAGETIME_0   = 2;
    public static final int DAMAGETIME_1   = 3;


    public static final int HP_MIN_WEAK         = 200;
    public static final int HP_MAX_WEAK         = 200;
    public static final int DAMAGE_0_WEAK       = 8;
    public static final int DAMAGE_1_WEAK       = 12;
    public static final int DAMAGETIME_0_WEAK   = 2;
    public static final int DAMAGETIME_1_WEAK   = 3;


    private int hpMinVal;
    private int hpMaxVal;
    private int damageVal0;
    private int damageVal1;
    private int damageTimeVal0;
    private int damageTimeVal1;


    // 怪物碰撞箱
    private static final float HB_X = 0F;
    private static final float HB_Y = 0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;

    // 绘制坐标
    public static final float DRAW_X = 1600.0F;
    public static final float DRAW_Y = 400.0F;

    // 贴图
    private static final String imgPath = imagePath("monsters/" + SakiMachineMonster.class.getSimpleName() + ".png");

    public float targetDrawX;  // 目标的绘制X位置
    public float targetDrawY;

    public float moveSpeed;    // 每秒移动的像素数
    public boolean isMoving;   // 是否正在移动
    public float duration;

    public int distance;


    public SakiMachineMonster(float x, float y) {
        // 先用原版的 maxHP 初始化 parent
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, imgPath, x, y);

        if (isHardMode) {
            this.hpMinVal = HP_MIN;
            this.hpMaxVal = HP_MAX;
            this.damageVal0 = DAMAGE_0;
            this.damageVal1 = DAMAGE_1;
            this.damageTimeVal0 = DAMAGETIME_0;
            this.damageTimeVal1 = DAMAGETIME_1;
        } else {
            this.hpMinVal = HP_MIN_WEAK;
            this.hpMaxVal = HP_MAX_WEAK;
            this.damageVal0 = DAMAGE_0_WEAK;
            this.damageVal1 = DAMAGE_1_WEAK;
            this.damageTimeVal0 = DAMAGETIME_0_WEAK;
            this.damageTimeVal1 = DAMAGETIME_1_WEAK;
        }

        // 设置血量
        setHp(this.hpMinVal, this.hpMaxVal);
        this.type = EnemyType.BOSS;

        // 对话框位置
        this.dialogX = this.hb_x + -50.0F * Settings.scale;
        this.dialogY = this.hb_y + 50.0F * Settings.scale;

        // 绘制坐标
        this.drawX = DRAW_X * Settings.scale;
        this.drawY = DRAW_Y * Settings.scale;

        // 将伤害Info放进 damage 列表 (index=0,1)
        this.damage.add(new DamageInfo(this, damageVal0, DamageInfo.DamageType.NORMAL)); // index 0
        this.damage.add(new DamageInfo(this, damageVal1, DamageInfo.DamageType.NORMAL)); // index 1

        //this.targetDrawX = this.drawX;
        this.moveSpeed = 0;
        this.isMoving = false;
        this.duration=0;

    }

    public void move(float duration){
        if(distance > 1){
            moveLeft(duration);
        } else if(distance == 1){
            // 根据与玩家的垂直距离决定上下移动
            if(AbstractDungeon.player.drawY - this.drawY > MutsumiOperatorMonster.STANDARDDISTANCE/2) {
                moveUp(duration);
            } else if(this.drawY - AbstractDungeon.player.drawY > MutsumiOperatorMonster.STANDARDDISTANCE/2) {
                moveDown(duration);
            } else {
                moveLeft(duration);
            }
        }
    }

    public void moveLeft(float duration) {
        // 设定目标X坐标，向左移动
        this.targetDrawX = this.targetDrawX - MutsumiOperatorMonster.STANDARDDISTANCE * Settings.scale;
        // 同时保持Y坐标不变
        // 初始化duration, 更新distance等操作
        this.duration = duration;
        distance--;
        this.isMoving = true;
    }

    public void moveDown(float duration) {
        // 设定目标Y坐标，向上移动
        this.targetDrawY = this.targetDrawY - MutsumiOperatorMonster.STANDARDDISTANCE * Settings.scale;
        // 同时保持X坐标不变
        this.duration = duration;
        distance--;
        this.isMoving = true;
    }

    public void moveUp(float duration) {
        // 设定目标Y坐标，向下移动（注意：这里与moveUp不同，增加Y坐标）
        this.targetDrawY = this.targetDrawY + MutsumiOperatorMonster.STANDARDDISTANCE * Settings.scale;
        this.duration = duration;
        distance--;
        this.isMoving = true;
    }

    @Override
    public void update() {
        // 调用父类的更新方法
        super.update();

        if (isMoving) {
            // 获取delta时间
            float delta = Gdx.graphics.getDeltaTime();
            // 为防止duration变为负值
            duration = Math.max(duration - delta, 0);

            // 分别计算X和Y方向的速度
            float speedX = (this.targetDrawX - this.drawX) / (duration > 0 ? duration : delta);
            float speedY = (this.targetDrawY - this.drawY) / (duration > 0 ? duration : delta);

            // 根据delta更新位置
            this.drawX += speedX * delta;
            this.drawY += speedY * delta;

            // 当duration耗尽时，认为到达目标位置
            if(duration <= 0){
                isMoving = false;
                // 更新描述（例如Power的描述）
                getPower(makeID("SakiMachineDistancePower")).updateDescription();
                if(distance <= 0){
                    // 当达到最低移动距离后，设置死亡逻辑
                    AbstractDungeon.player.isDead = true;
                    AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                    AbstractDungeon.player.currentHealth = 0;
                    if (AbstractDungeon.player.currentBlock > 0) {
                        AbstractDungeon.player.loseBlock();
                    }
                }
            }
        }
    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                damagePlayer(0, damageTimeVal0);
                break;
            case 1:
                damagePlayer(1, damageTimeVal1);
                break;
        }
        // 回合结束后，准备下一次动作
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        int rand = AbstractDungeon.miscRng.random(0, 1);
        if (rand == 0) {
            setMove((byte) 0, Intent.ATTACK,
                    this.damage.get(0).base, damageTimeVal0, true);
        } else {
            setMove((byte) 1, Intent.ATTACK,
                    this.damage.get(1).base, damageTimeVal1, true);
        }
    }


    @Override
    public void die() {
        super.die();
        if (this.currentHealth <= 0) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
        }
    }
}
