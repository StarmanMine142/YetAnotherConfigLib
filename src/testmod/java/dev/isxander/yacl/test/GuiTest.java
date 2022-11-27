package dev.isxander.yacl.test;

import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.RequireRestartScreen;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import dev.isxander.yacl.gui.controllers.slider.DoubleSliderController;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl.gui.controllers.slider.LongSliderController;
import dev.isxander.yacl.gui.controllers.string.StringController;
import dev.isxander.yacl.test.config.ConfigData;
import dev.isxander.yacl.test.config.Entrypoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

import java.awt.*;

public class GuiTest {
    public static Screen getModConfigScreenFactory(Screen parent) {
        return Entrypoint.getConfig().buildConfig((config, builder) -> builder
                    .title(Text.of("Test Suites"))
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("Suites"))
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Full Test Suite"))
                                    .controller(ActionController::new)
                                    .action((screen, opt) -> MinecraftClient.getInstance().setScreen(getFullTestSuite(screen)))
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Basic Wiki Suite"))
                                    .controller(ActionController::new)
                                    .action((screen, opt) -> MinecraftClient.getInstance().setScreen(getWikiBasic(screen)))
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Group Wiki Suite"))
                                    .controller(ActionController::new)
                                    .action((screen, opt) -> MinecraftClient.getInstance().setScreen(getWikiGroups(screen)))
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Unavailable Test Suite"))
                                    .controller(ActionController::new)
                                    .action((screen, opt) -> MinecraftClient.getInstance().setScreen(getDisabledTest(screen)))
                                    .build())
                            .build())
                )
                .generateScreen(parent);
    }

    private static Screen getFullTestSuite(Screen parent) {
        return Entrypoint.getConfig().buildConfig((config, builder) -> builder
                    .title(Text.of("Test GUI"))
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("Control Examples"))
                            .tooltip(Text.of("Example Category Description"))
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Boolean Controllers"))
                                    .tooltip(Text.of("Test!"))
                                    .collapsed(true)
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("Boolean Toggle"))
                                            .tooltip(value -> Text.of("A simple toggle button that contains the value '" + value + "'"))
                                            .binding(
                                                    config.getDefaults().booleanToggle,
                                                    () -> config.getConfig().booleanToggle,
                                                    (value) -> config.getConfig().booleanToggle = value
                                            )
                                            .controller(BooleanController::new)
                                            .flag(OptionFlag.GAME_RESTART)
                                            .available(false)
                                            .build())
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("Custom Boolean Toggle"))
                                            .tooltip(Text.of("You can customize these controllers like this!"))
                                            .binding(
                                                    config.getDefaults().customBooleanToggle,
                                                    () -> config.getConfig().customBooleanToggle,
                                                    (value) -> config.getConfig().customBooleanToggle = value
                                            )
                                            .controller(opt -> new BooleanController(opt, state -> state ? Text.of("Amazing") : Text.of("Not Amazing"), true))
                                            .build())
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("Tick Box aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
                                            .tooltip(Text.of("There are even alternate methods of displaying the same data type!"))
                                            .binding(
                                                    config.getDefaults().tickbox,
                                                    () -> config.getConfig().tickbox,
                                                    (value) -> config.getConfig().tickbox = value
                                            )
                                            .controller(TickBoxController::new)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Slider Controllers"))
                                    .option(Option.createBuilder(int.class)
                                            .name(Text.of("Int Slider that is cut off because the slider"))
                                            .instant(true)
                                            .binding(
                                                    config.getDefaults().intSlider,
                                                    () -> config.getConfig().intSlider,
                                                    value -> config.getConfig().intSlider = value

                                            )
                                            .controller(opt -> new IntegerSliderController(opt, 0, 3, 1))
                                            .build())
                                    .option(Option.createBuilder(double.class)
                                            .name(Text.of("Double Slider"))
                                            .binding(
                                                    config.getDefaults().doubleSlider,
                                                    () -> config.getConfig().doubleSlider,
                                                    (value) -> config.getConfig().doubleSlider = value
                                            )
                                            .controller(opt -> new DoubleSliderController(opt, 0, 3, 0.05))
                                            .build())
                                    .option(Option.createBuilder(float.class)
                                            .name(Text.of("Float Slider"))
                                            .binding(
                                                    config.getDefaults().floatSlider,
                                                    () -> config.getConfig().floatSlider,
                                                    (value) -> config.getConfig().floatSlider = value
                                            )
                                            .controller(opt -> new FloatSliderController(opt, 0, 3, 0.1f))
                                            .build())
                                    .option(Option.createBuilder(long.class)
                                            .name(Text.of("Long Slider"))
                                            .binding(
                                                    config.getDefaults().longSlider,
                                                    () -> config.getConfig().longSlider,
                                                    (value) -> config.getConfig().longSlider = value
                                            )
                                            .controller(opt -> new LongSliderController(opt, 0, 1_000_000, 100))
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Input Field Controllers"))
                                    .option(Option.createBuilder(String.class)
                                            .name(Text.of("Text Option"))
                                            .binding(
                                                    config.getDefaults().textField,
                                                    () -> config.getConfig().textField,
                                                    value -> config.getConfig().textField = value
                                            )
                                            .controller(StringController::new)
                                            .build())
                                    .option(Option.createBuilder(Color.class)
                                            .name(Text.of("Color Option"))
                                            .binding(
                                                    config.getDefaults().colorOption,
                                                    () -> config.getConfig().colorOption,
                                                    value -> config.getConfig().colorOption = value
                                            )
                                            .controller(ColorController::new)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Enum Controllers"))
                                    .option(Option.createBuilder(ConfigData.Alphabet.class)
                                            .name(Text.of("Enum Cycler"))
                                            .binding(
                                                    config.getDefaults().enumOption,
                                                    () -> config.getConfig().enumOption,
                                                    (value) -> config.getConfig().enumOption = value
                                            )
                                            .controller(EnumController::new)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Options that aren't really options"))
                                    .option(ButtonOption.createBuilder()
                                            .name(Text.of("Button \"Option\""))
                                            .action((screen, opt) -> SystemToast.add(MinecraftClient.getInstance().getToastManager(), SystemToast.Type.TUTORIAL_HINT, Text.of("Button Pressed"), Text.of("Button option was invoked!")))
                                            .controller(ActionController::new)
                                            .build())
                                    .option(Option.createBuilder(Text.class)
                                            .binding(Binding.immutable(Text.empty()
                                                    .append(Text.literal("a").styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("a")))))
                                                    .append(Text.literal("b").styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("b")))))
                                                    .append(Text.literal("c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c c").styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("c")))))
                                                    .append(Text.literal("e").styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("e")))))
                                                    .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://isxander.dev")))
                                            ))
                                            .controller(LabelController::new)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Minecraft Bindings"))
                                    .tooltip(Text.of("YACL can also bind Minecraft options!"))
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("Minecraft AutoJump"))
                                            .tooltip(Text.of("You can even bind minecraft options!"))
                                            .binding(Binding.minecraft(MinecraftClient.getInstance().options.getAutoJump()))
                                            .controller(TickBoxController::new)
                                            .build())
                                    .option(Option.createBuilder(GraphicsMode.class)
                                            .name(Text.of("Minecraft Graphics Mode"))
                                            .binding(Binding.minecraft(MinecraftClient.getInstance().options.getGraphicsMode()))
                                            .controller(EnumController::new)
                                            .build())
                                    .build())
                            .build())
                    .category(PlaceholderCategory.createBuilder()
                            .name(Text.of("Placeholder Category"))
                            .screen((client, yaclScreen) -> new RequireRestartScreen(yaclScreen))
                            .build())
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("Group Test"))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.of("Root Test"))
                                    .binding(
                                            config.getDefaults().groupTestRoot,
                                            () -> config.getConfig().groupTestRoot,
                                            value -> config.getConfig().groupTestRoot = value
                                    )
                                    .controller(TickBoxController::new)
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("First Group"))
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("First Group Test 1"))
                                            .binding(
                                                    config.getDefaults().groupTestFirstGroup,
                                                    () -> config.getConfig().groupTestFirstGroup,
                                                    value -> config.getConfig().groupTestFirstGroup = value
                                            )
                                            .controller(TickBoxController::new)
                                            .build())
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("First Group Test 2"))
                                            .binding(
                                                    config.getDefaults().groupTestFirstGroup2,
                                                    () -> config.getConfig().groupTestFirstGroup2,
                                                    value -> config.getConfig().groupTestFirstGroup2 = value
                                            )
                                            .controller(TickBoxController::new)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Text.empty())
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("Second Group Test"))
                                            .binding(
                                                    config.getDefaults().groupTestSecondGroup,
                                                    () -> config.getConfig().groupTestSecondGroup,
                                                    value -> config.getConfig().groupTestSecondGroup = value
                                            )
                                            .controller(TickBoxController::new)
                                            .build())
                                    .build())
                            .build())
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("Scroll Test"))
                            .option(Option.createBuilder(int.class)
                                    .name(Text.of("Int Slider that is cut off because the slider"))
                                    .binding(
                                            config.getDefaults().scrollingSlider,
                                            () -> config.getConfig().scrollingSlider,
                                            (value) -> config.getConfig().scrollingSlider = value
                                    )
                                    .controller(opt -> new IntegerSliderController(opt, 0, 10, 1))
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .option(ButtonOption.createBuilder()
                                    .name(Text.of("Option"))
                                    .action((screen, opt) -> {})
                                    .controller(ActionController::new)
                                    .build())
                            .build())
                    .save(() -> {
                        MinecraftClient.getInstance().options.write();
                        config.save();
                    })
                )
                .generateScreen(parent);
    }

    private static Screen getDisabledTest(Screen parent) {
        return Entrypoint.getConfig().buildConfig((config, builder) -> builder
                    .title(Text.empty())
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("Disabled Test"))
                            .option(Option.createBuilder(int.class)
                                    .name(Text.of("Slider"))
                                    .binding(Binding.immutable(0))
                                    .controller(opt -> new IntegerSliderController(opt, 0, 5, 1))
                                    .available(false)
                                    .build())
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.of("Tick Box"))
                                    .binding(Binding.immutable(true))
                                    .controller(TickBoxController::new)
                                    .available(false)
                                    .build())
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.of("Tick Box (Enabled)"))
                                    .binding(Binding.immutable(true))
                                    .controller(TickBoxController::new)
                                    .build())
                            .option(Option.createBuilder(String.class)
                                    .name(Text.of("Text Field"))
                                    .binding(Binding.immutable("hi"))
                                    .controller(StringController::new)
                                    .available(false)
                                    .build())
                            .build())
                )
                .generateScreen(parent);
    }

    private static Screen getWikiBasic(Screen parent) {
        return Entrypoint.getConfig().buildConfig((config, builder) -> builder
                    .title(Text.of("Mod Name"))
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("My Category"))
                            .tooltip(Text.of("This displays when you hover over a category button")) // optional
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.of("My Boolean Option"))
                                    .tooltip(Text.of("This option displays the basic capabilities of YetAnotherConfigLib")) // optional
                                    .binding(
                                            config.getDefaults().booleanToggle, // default
                                            () -> config.getConfig().booleanToggle, // getter
                                            newValue -> config.getConfig().booleanToggle = newValue // setter
                                    )
                                    .controller(BooleanController::new)
                                    .build())
                            .build())
                )
                .generateScreen(parent);
    }

    private static Screen getWikiGroups(Screen parent) {
        return Entrypoint.getConfig().buildConfig((config, builder) -> builder
                    .title(Text.of("Mod Name"))
                    .category(ConfigCategory.createBuilder()
                            .name(Text.of("My Category"))
                            .tooltip(Text.of("This displays when you hover over a category button")) // optional
                            .group(OptionGroup.createBuilder()
                                    .name(Text.of("Option Group"))
                                    .option(Option.createBuilder(boolean.class)
                                            .name(Text.of("My Boolean Option"))
                                            .tooltip(Text.of("This option displays the basic capabilities of YetAnotherConfigLib")) // optional
                                            .binding(
                                                    config.getDefaults().booleanToggle, // default
                                                    () -> config.getConfig().booleanToggle, // getter
                                                    newValue -> config.getConfig().booleanToggle = newValue // setter
                                            )
                                            .controller(BooleanController::new)
                                            .build())
                                    .build())
                            .build())
                )
                .generateScreen(parent);
    }
}