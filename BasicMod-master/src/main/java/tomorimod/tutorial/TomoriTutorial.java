package tomorimod.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import tomorimod.util.TextureLoader;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class TomoriTutorial extends FtueTip {
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString(makeID("Tomori"));
    public static final String[] txt = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;

    private static int picWidth = 800;
    private static int picHeight = 650;
    private static int maxSlot = 5;
    private static float paddingLeft = 500.0F;

    private Color screen = Color.valueOf("1c262a00");
    private float scrollTimer = 0.0F;
    private int currentPage = 1;
    private int pageNum = 0;
    private Texture[] img = new Texture[maxSlot + 1];
    private float x0;
    private float[] pageX;

    public TomoriTutorial() {
        for(int i=1;i<6;i++){
            String path=imagePath("toturial/"+i+".png");
            this.img[++this.pageNum] = TextureLoader.getTexture(path);
        }
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x0 = 0.0F;

        this.pageX = new float[maxSlot + 1];
        for (int i = 1; i <= this.pageNum; i++) {
            this.pageX[i] = paddingLeft * Settings.scale + ((i - 1) * Settings.WIDTH);
        }
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    private float targetX;
    private float startX;

    public void update() {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
        if (this.currentPage >= this.pageNum) {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        }
        if (this.screen.a != 0.8F) {
            this.screen.a += Gdx.graphics.getDeltaTime();
            if (this.screen.a > 0.8F) {
                this.screen.a = 0.8F;
            }
        }
        if ((AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft) || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (this.currentPage >= this.pageNum) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
            }
            this.currentPage++;
            this.startX = this.x0;
            this.targetX = ((1 - this.currentPage) * Settings.WIDTH);
            this.scrollTimer = 0.3F;
            if (this.currentPage >= this.pageNum) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
            }
        }
        if (this.scrollTimer != 0.0F) {
            this.scrollTimer -= Gdx.graphics.getDeltaTime();
            if (this.scrollTimer < 0.0F) {
                this.scrollTimer = 0.0F;
            }
        }
        this.x0 = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE);

        for (int i = 1; i <= this.pageNum; i++) {
            sb.draw(this.img[i], this.x0 + this.pageX[i] - picWidth / 2.0F, Settings.HEIGHT / 2.0F - picHeight / 2.0F, picWidth / 2.0F, picHeight / 2.0F, picWidth, picHeight, Settings.scale, Settings.scale, 0.0F, 0, 0, picWidth, picHeight, false, false);
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, txt[i - 1], this.x0 + this.pageX[i] + 450.0F * Settings.scale, Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, txt[i - 1], 750.0F * Settings.scale, 50.0F * Settings.scale) / 2.0F, 750.0F * Settings.scale, 50.0F * Settings.scale, Settings.CREAM_COLOR);
        }

        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + this.currentPage + "/" + this.pageNum + LABEL[4], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);

        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }
}