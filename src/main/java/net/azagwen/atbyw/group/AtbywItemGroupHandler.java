package net.azagwen.atbyw.group;

import com.google.common.collect.Lists;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.mixin.accessor.HandledScreenAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.CreativeScreenHandler;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

//This handler is used in the creative inventory mixin
public class AtbywItemGroupHandler {
    public static final Identifier MEDIA_ICON_TEXTURE = AtbywMain.id("textures/gui/info_button.png");
    public final String curseforgeLink = "https://www.curseforge.com/minecraft/mc-mods/atbyw";
    public final String gitlabLink = "https://gitlab.com/Azagwen/ATBYW";
    public final String githubLink = "https://github.com/Azagwen/ATBYW";
    public final String crowdinLink = "https://crowdin.com/translate/atbyw";
    public final List<TexturedButtonWidget> mediaButtons = Lists.newArrayList();
    public final List<ItemGroupTabWidget> tabButtons = Lists.newArrayList();
    public ItemGroupTabWidget selectedSubtab;
    private final AbstractInventoryScreen<CreativeScreenHandler> creativeScreen;

    public AtbywItemGroupHandler(AbstractInventoryScreen<CreativeScreenHandler> creativeScreen) {
        this.creativeScreen = creativeScreen;
    }

    public void renderAtbywItemGroup(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.tabButtons.forEach(button -> {
            if(button.isHovered()) {
                this.creativeScreen.renderTooltip(matrixStack, button.getMessage(), mouseX, mouseY);
            }
        });
        this.mediaButtons.forEach(button -> {
            if(button.isHovered()) {
                this.creativeScreen.renderTooltip(matrixStack, button.getMessage(), mouseX, mouseY);
            }
        });
    }

    public void handleAtbywItemGroup(ItemGroup group) {
        var screen = (HandledScreenAccessor) this.creativeScreen;
        var x = screen.getX();
        var y = screen.getY();

        for (var button : this.tabButtons) {
            screen.invokeRemove(button);
        }
        for (var button : this.mediaButtons) {
            screen.invokeRemove(button);
        }
        this.tabButtons.clear();
        this.mediaButtons.clear();

        if(group instanceof TabbedItemGroup tabbedGroup) {
            if(!tabbedGroup.hasInitialized()) {
                tabbedGroup.initialize();
            }

            int i = 0;
            for(var tab : tabbedGroup.getTabs()) {
                var selectTab = i;
                var flipTab = i > 3;
                var xOffset = flipTab ? (x + 191) : (x - 29);
                var yOffset = flipTab ? (y + 12) + ((i - 4) * 30) : (y + 12) + (i * 30);
                var tabWidget = new ItemGroupTabWidget(xOffset, yOffset, flipTab, tab, (button)-> {
                    tabbedGroup.setSelectedTab(selectTab);
                    MinecraftClient.getInstance().setScreen(this.creativeScreen);
                    ((ItemGroupTabWidget) button).isSelected = true;
                    this.selectedSubtab = (ItemGroupTabWidget) button;
                });

                if(i == tabbedGroup.getSelectedTabIndex()) {
                    this.selectedSubtab = tabWidget;
                    tabWidget.isSelected = true;
                }

                this.tabButtons.add(tabWidget);
                screen.invokeAddDrawableChild(tabWidget);
                i++;
            }
        }

        if(group instanceof AtbywItemGroup) {
            var curseforgeButton = this.createMediaButton(x + 175, y + 4, 24, 0, "curseforgeLink", this.curseforgeLink);
            var gitlabButton = this.createMediaButton(x + 161, y + 4, 12, 0, "gitlabLink", this.gitlabLink);

            this.mediaButtons.add(curseforgeButton);
            this.mediaButtons.add(gitlabButton);

            screen.invokeAddDrawableChild(curseforgeButton);
            screen.invokeAddDrawableChild(gitlabButton);
        }
    }

    public TexturedButtonWidget createMediaButton(int offsetX, int offsetY, int offsetU, int offsetV, String name, String link) {
        var client = MinecraftClient.getInstance();
        var tooltip = new TranslatableText("itemGroup." + AtbywMain.ATBYW + "." + name);
        var linkScreen = new ConfirmChatLinkScreen((opened) -> this.openLinkAction(client, opened, link), link, true);
        return new TexturedButtonWidget(offsetX, offsetY, 12, 12, offsetU, offsetV, 12, MEDIA_ICON_TEXTURE, 64, 64, (button) -> client.setScreen(linkScreen), tooltip);
    }

    public void openLinkAction(MinecraftClient client, boolean opened, String link) {
        if (opened) {
            Util.getOperatingSystem().open(link);
        }
        client.setScreen(this.creativeScreen);
    }
}
