package customclient.gui;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import customclient.Main;
import customclient.acount.MicrosoftLogin;
import customclient.acount.MicrosoftLogin.LoginData;
import customclient.file.ClientFileManager;
import customclient.gui.GuiButtonCosmetics;
import customclient.gui.GuiButtonOptions;
import customclient.gui.GuiButtonQuit;
import customclient.gui.GuiButtonTransparent;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.CustomPanorama;
import net.optifine.CustomPanoramaProperties;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;



public class GuiScreenLogin extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();

    /** Counts the number of screen updates. */
    private float updateCounter;

    /** The splash message. */
    private String splashText;
    private GuiButton buttonResetDemo;

    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;

    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();



    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("customclient/textures/gui/icon/client.png");

    /** An array of all the paths to the panorama pictures. */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {
    		new ResourceLocation("customclient/textures/panorama/npanorama_1.png"), 
    		new ResourceLocation("customclient/textures/panorama/npanorama_1.png"), 
    		new ResourceLocation("customclient/textures/panorama/npanorama_2.png"),
    		new ResourceLocation("customclient/textures/panorama/npanorama_3.png"), 
    		new ResourceLocation("customclient/textures/panorama/npanorama_4.png"), 
    		new ResourceLocation("customclient/textures/panorama/npanorama_5.png")
    		};
    
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";

    private ResourceLocation backgroundTexture;


    private boolean field_183502_L;
    private GuiScreen field_183503_M;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;

    public GuiScreenLogin()
    {
       
        this.field_183502_L = false;

        this.updateCounter = RANDOM.nextFloat();

    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.panoramaTimer;

    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        


        int i = 24;
        int j = this.height / 4 + 48;

        this.addButtons(j, 24);


        this.mc.func_181537_a(false);

        if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L)
        {
            RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183502_L = true;
        }

    }
    GuiButtonTransparent signin;
    GuiButtonTransparent start;

    private void addButtons(int p_73969_1_, int p_73969_2_)
    {

    	this.buttonList.add(signin=new GuiButtonTransparent(2, this.width / 2 - 100 + 25, p_73969_1_ + p_73969_2_ + 100 -45, 150, 20, "Sign in with Microsoft"));
        this.buttonList.add(start=new GuiButtonTransparent(3, this.width / 2-45, p_73969_1_ + p_73969_2_ + 170, 90, 20, "Get Started"));
    }

    private boolean markSignedIn = false;

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 2) {
        	Consumer<String> str = new Consumer<String>() {
                @Override
                public void accept(String s) {
                    LoginData ld = MicrosoftLogin.login(s);
                    mc.session = new Session(ld.username, ld.uuid, ld.mcToken, "legacy");
                    MicrosoftLogin.startServer();
                    ClientFileManager.saveSession(mc.session);
                    
                    markSignedIn = true;
                	System.out.println("Logged in as " + ld.username);
                }
            };
            
                
            MicrosoftLogin.startServer();
            MicrosoftLogin.getRefreshToken(str);
        }else if(button.id == 3) {
        	
    		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
    		    try {
					Desktop.getDesktop().browse(new URI("https://www.minecraft.net/en-us/login?view=mojang"));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;
        int j = 64;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            j = custompanoramaproperties.getBlur1();
        }

        for (int k = 0; k < j; ++k)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(k % i) / (float)i - 0.5F) / 64.0F;
            float f1 = ((float)(k / i) / (float)i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int l = 0; l < 6; ++l)
            {
                GlStateManager.pushMatrix();

                if (l == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (l == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (l == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                ResourceLocation[] aresourcelocation = titlePanoramaPaths;

                if (custompanoramaproperties != null)
                {
                    aresourcelocation = custompanoramaproperties.getPanoramaLocations();
                }

                this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                int i1 = 255 / (k + 1);
                float f3 = 0.0F;
                worldrenderer.func_181662_b(-1.0D, -1.0D, 1.0D).func_181673_a(0.0D, 0.0D).func_181669_b(255, 255, 255, i1).func_181675_d();
                worldrenderer.func_181662_b(1.0D, -1.0D, 1.0D).func_181673_a(1.0D, 0.0D).func_181669_b(255, 255, 255, i1).func_181675_d();
                worldrenderer.func_181662_b(1.0D, 1.0D, 1.0D).func_181673_a(1.0D, 1.0D).func_181669_b(255, 255, 255, i1).func_181675_d();
                worldrenderer.func_181662_b(-1.0D, 1.0D, 1.0D).func_181673_a(0.0D, 1.0D).func_181669_b(255, 255, 255, i1).func_181675_d();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox(float p_73968_1_)
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        GlStateManager.disableAlpha();
        int i = 3;
        int j = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            j = custompanoramaproperties.getBlur2();
        }

        for (int k = 0; k < j; ++k)
        {
            float f = 1.0F / (float)(k + 1);
            int l = this.width;
            int i1 = this.height;
            float f1 = (float)(k - i / 2) / 256.0F;
            worldrenderer.func_181662_b((double)l, (double)i1, (double)this.zLevel).func_181673_a((double)(0.0F + f1), 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).func_181675_d();
            worldrenderer.func_181662_b((double)l, 0.0D, (double)this.zLevel).func_181673_a((double)(1.0F + f1), 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).func_181675_d();
            worldrenderer.func_181662_b(0.0D, 0.0D, (double)this.zLevel).func_181673_a((double)(1.0F + f1), 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).func_181675_d();
            worldrenderer.func_181662_b(0.0D, (double)i1, (double)this.zLevel).func_181673_a((double)(0.0F + f1), 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).func_181675_d();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        int i = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            i = custompanoramaproperties.getBlur3();
        }

        for (int j = 0; j < i; ++j)
        {
            this.rotateAndBlurSkybox(p_73971_3_);
            this.rotateAndBlurSkybox(p_73971_3_);
        }

        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f2 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float f = (float)this.height * f2 / 256.0F;
        float f1 = (float)this.width * f2 / 256.0F;
        int k = this.width;
        int l = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        worldrenderer.func_181662_b(0.0D, (double)l, (double)this.zLevel).func_181673_a((double)(0.5F - f), (double)(0.5F + f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
        worldrenderer.func_181662_b((double)k, (double)l, (double)this.zLevel).func_181673_a((double)(0.5F - f), (double)(0.5F - f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
        worldrenderer.func_181662_b((double)k, 0.0D, (double)this.zLevel).func_181673_a((double)(0.5F + f), (double)(0.5F - f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
        worldrenderer.func_181662_b(0.0D, 0.0D, (double)this.zLevel).func_181673_a((double)(0.5F + f), (double)(0.5F + f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).func_181675_d();
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	if(markSignedIn) {
    		ClientFileManager.loadCrafatarRender(mc.session, Type.SKIN, false, 10f, new SkinManager.SkinAvailableCallback() {

				@Override
				public void skinAvailable(Type p_180521_1_, ResourceLocation location, MinecraftProfileTexture profileTexture) {
					Main.INSTANCE.currentPlayerHead = location;
					System.out.println(location.getResourcePath());
				}
            	
            });
    		mc.displayGuiScreen(new GuiMainMenu());
    	}
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 274;
        int j = this.width / 2 - i / 2;
        int k = 30;
        int l = -2130706433;
        int i1 = 16777215;
        int j1 = 0;
        int k1 = Integer.MIN_VALUE;


        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


        ScaledResolution sr = new ScaledResolution(mc);
        
        
        int x = sr.getScaledWidth();
        int y = sr.getScaledHeight();

        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GlStateManager.color(1, 1, 1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1, (500f)/(700f), 1);
        GlStateManager.enableBlend();
        this.drawTexturedModalRect(x/2-127, y/2-150, 0, 0, 512/2, 512/2);
        GlStateManager.popMatrix();
        
        int width = 300;
        int height = 125;
        
        int yOff = 10;
        
        this.drawRect(x/2-width/2, y/2+yOff,x/2+width/2, y/2+height+yOff, new Color(0, 0, 0, 125).getRGB());
        signin.yPosition = y/2+yOff+10;
        start.yPosition = y/2+height+yOff-25;
        this.drawCenteredString(mc.fontRendererObj, "You'll need to migrate to a Microsoft account to log in.", x/2, y/2+height+yOff-50, Color.white.getRGB());

    
        GlStateManager.pushMatrix();
        GlStateManager.scale(1, 0.5, 1);
        Gui.drawHorizontalLine(x/2-75, x/2+75, y+height+yOff-10, Color.white.getRGB());
        GlStateManager.popMatrix();
        
        String s = "Minecraft 1.8.9";

        if (this.mc.isDemo())
        {
            s = s + " Demo";
        }

        this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
        String s2 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, s2, this.width - this.fontRendererObj.getStringWidth(s2) - 2, this.height - 10, -1);


        super.drawScreen(mouseX, mouseY, partialTicks);


        if (this.modUpdateNotification != null)
        {
            this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (this.field_183503_M != null)
        {
            this.field_183503_M.onGuiClosed();
        }
    }
}
