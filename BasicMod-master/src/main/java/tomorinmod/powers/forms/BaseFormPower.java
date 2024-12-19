package tomorinmod.powers.forms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorinmod.cards.forms.forms.BaseFormCard;
import tomorinmod.powers.BasePower;
import tomorinmod.util.TextureLoader;

import java.util.HashMap;
import java.util.Map;

import static tomorinmod.BasicMod.makeID;
import static tomorinmod.BasicMod.powerPath;
import static tomorinmod.util.CustomUtils.idToName;
import static tomorinmod.util.TextureLoader.getTexture;
import static tomorinmod.util.TextureLoader.getTextureNull;

public class BaseFormPower extends BasePower {

    public BaseFormPower(String powerID,PowerType type,boolean turnBased,AbstractCreature owner,int amount) {
        super(powerID, type, turnBased, owner, amount);
    }

    @Override
    public void onRemove(){
        if(AbstractDungeon.player.hasRelic(makeID("MicrophoneRelic"))){
            if(this instanceof FormEffect){
                ((FormEffect)this).applyEffectPower();
            }
        }
    }

    @Override
    public void updateDescription(){
        if(AbstractDungeon.player.hasRelic(makeID("SystemRelic"))){
            description=" #y永久形态 ，"+description;
        }
    }

    // gpt的缓存优化
    private static final Map<String, Texture> textureCache = new HashMap<>();

    private static final Map<String, TextureAtlas.AtlasRegion> atlasRegionCache = new HashMap<>();


    private static final AssetManager assetManager = new AssetManager();
    /**
     * 根据路径获取缓存的Texture，如果不存在则加载并缓存
     * @param path 纹理的路径
     * @return 缓存的Texture对象
     */
    public static Texture getCachedTexture(String path) {
        if (!assetManager.isLoaded(path)) {
            assetManager.load(path, Texture.class);
            assetManager.finishLoadingAsset(path); // 确保加载完成
        }
        return assetManager.get(path, Texture.class);
    }

    public static void dispose() {
        assetManager.dispose();
    }

    /**
     * 获取缓存的 AtlasRegion，如果不存在则创建并缓存
     * @param texture 纹理对象
     * @return 缓存的AtlasRegion对象
     */
    private static TextureAtlas.AtlasRegion getCachedAtlasRegion(Texture texture) {
        String key = texture.toString();
        return atlasRegionCache.computeIfAbsent(key, k -> {
            System.out.println("创建AtlasRegion: " + key);
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        });
    }

    /**
     * 根据指定的颜色更改BaseFormPower的纹理和区域
     * @param power 目标对象
     * @param color 颜色（green, red, 其他）
     */
    public static void changeColor(BaseFormPower power, String color) {
        String powerName = idToName(power.ID);
        Texture normalTexture;
        Texture hiDefImage;

        if ("green".equals(color)) {
            normalTexture = getCachedTexture(powerPath(powerName+".png"));
            hiDefImage = getCachedTexture(powerPath("large/"+powerName+".png"));
        } else if ("red".equals(color)) {
            normalTexture = getCachedTexture(powerPath("red/output32/" + powerName + "_red.png"));
            hiDefImage = getCachedTexture(powerPath("red/output84/" + powerName + "_red.png"));
        } else {
            normalTexture = null;
            hiDefImage = null;
        }

        if (normalTexture != null) {
            power.region48 = getCachedAtlasRegion(normalTexture);
        }
        if (hiDefImage != null) {
            power.region128 = getCachedAtlasRegion(hiDefImage);
        }
    }

    public static void addDescription(BaseFormPower power){
        power.description=power.description+"（ #y当前形态 ）";
    }

    public static void removeDescription(BaseFormPower power){
        power.description = power.description.replace("（ #y当前形态 ）", "");
    }

}
