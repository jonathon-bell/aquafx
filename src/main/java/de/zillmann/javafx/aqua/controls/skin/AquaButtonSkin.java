package de.zillmann.javafx.aqua.controls.skin;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundFillBuilder;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import de.zillmann.javafx.aqua.util.BindableTransition;

public class AquaButtonSkin extends ButtonSkin {

    private BindableTransition defaultButtonTransition;

    private String usualButtonStyle = "-fx-background-color: rgb(255, 255, 255),"
            + "linear-gradient(#ffffff 20%, #ececec 60%, #ececec 80%, #eeeeee 100%);"
            + "-fx-background-insets:  0, 1;"
            + "-fx-background-radius: 4, 4;"
            + "-fx-border-radius: 4;"
            + "-fx-border-width: 0.5;"
            + "-fx-border-color: rgb(129, 129, 129);";

    private String armedButtonStyle = "	-fx-background-color:"
            + "linear-gradient(rgb(190, 214, 237) 0%, rgb(178, 213, 237) 100% ),"
            + "linear-gradient(rgb(165, 193, 238) 0%, rgb(108, 161, 231) 50%,"
            + "rgb(74, 138, 217) 50%, rgb(105, 167, 236) 75%, rgb(152, 201, 238) 100%),"
            + "radial-gradient(focus-angle 180deg, focus-distance 95%, center 1% 50%,"
            + "radius 50%, #78b0ee, transparent),"
            + "radial-gradient(focus-angle 0deg, focus-distance 95%, center 100% 50%,"
            + "radius 50%, #78b0ee, transparent);"
            + "-fx-background-insets: 0, 0, 1 1 1 2, 1 2 1 1;"
            + "-fx-background-radius: 4, 4, 4, 4;"
            + "-fx-border-color: rgb(100, 103, 124);";

    public AquaButtonSkin(Button button) {
        super(button);
        registerChangeListener(button.disabledProperty(), "DISABLED");
        registerChangeListener(button.hoverProperty(), "HOVER");
        if (getSkinnable().isDefaultButton()) {
            /*
             * * were we already the defaultButton, before the listener was
             * added?* don't laugh, it can happen....
             */
            setDefaultButtonAnimation();
        }
        if (getSkinnable().isFocused()) {
            setFocusBorder();
        }else{
            setDropShadow();
        }
        /**
         * if the button is a default button, it has to stop blinking when
         * pressed
         */
        getSkinnable().setOnMousePressed(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                if (getSkinnable().isFocused()) {
                    setFocusBorder();
                }
                if (getSkinnable().isDefaultButton()) {
                    setDefaultButtonAnimation();
                    getSkinnable().setStyle(armedButtonStyle);
                }
            }
        });

        /**
         * if the button is default, the button has to start blinking again,
         * when mouse is released
         */
        getSkinnable().setOnMouseReleased(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                if (getSkinnable().isDefaultButton()) {
                    setDefaultButtonAnimation();
                }
            }
        });

        final ChangeListener<Boolean> windowFocusChangedListener = new ChangeListener<Boolean>() {

            @Override
            public void changed(
                    ObservableValue<? extends Boolean> observableValue,
                    Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    if (newValue.booleanValue()
                            && getSkinnable().isDefaultButton()) {
                        if (defaultButtonTransition != null
                                && defaultButtonTransition.getStatus() != Status.RUNNING) {
                            setDefaultButtonAnimation();
                        }
                    } else if (defaultButtonTransition != null
                            && defaultButtonTransition.getStatus() == Status.RUNNING) {
                        setDefaultButtonAnimation();

                        // button has to look like a usual button
                        getSkinnable().setStyle(usualButtonStyle);
                        getSkinnable().impl_reapplyCSS();
                    }
                }
            }
        };

        getSkinnable().sceneProperty().addListener(new ChangeListener<Scene>() {

            @Override
            public void changed(
                    ObservableValue<? extends Scene> observableValue,
                    Scene oldScene, Scene newScene) {
                if (oldScene != null && oldScene.getWindow() != null) {
                    oldScene.getWindow().focusedProperty()
                            .removeListener(windowFocusChangedListener);
                }
                if (newScene != null && newScene.getWindow() != null) {
                    newScene.getWindow().focusedProperty()
                            .addListener(windowFocusChangedListener);
                }
            }
        });

        if (getSkinnable().getScene() != null
                && getSkinnable().getScene().getWindow() != null) {
            getSkinnable().getScene().getWindow().focusedProperty()
                    .addListener(windowFocusChangedListener);
        }
    }

    private void setFocusBorder() {
        InnerShadow innerFocus = new InnerShadow();
        innerFocus.setColor(Color.rgb(104, 155, 201, 0.8));
        innerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        innerFocus.setRadius(5.0);
        innerFocus.setChoke(0.8);
        innerFocus.setOffsetX(0.0);
        DropShadow outerFocus = new DropShadow();
        outerFocus.setColor(Color.rgb(104, 155, 201));
        outerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        outerFocus.setRadius(6.5);
        outerFocus.setSpread(0.6);
        outerFocus.setOffsetX(0.0);
        outerFocus.setOffsetY(0.0);
        outerFocus.setInput(innerFocus);
        getSkinnable().setEffect(outerFocus);
    }

    private void setDropShadow() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(172, 172, 184));
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        dropShadow.setRadius(2.0);
        dropShadow.setSpread(0.1);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.8);
        getSkinnable().setEffect(dropShadow);
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if (p == "HOVER") {
            if (getSkinnable().isDefaultButton() && getSkinnable().isPressed()
                    && getSkinnable().isHover()) {
                getSkinnable().setStyle(armedButtonStyle);
            } else if (getSkinnable().isDefaultButton()
                    && getSkinnable().isPressed() && !getSkinnable().isHover()) {
                getSkinnable().setStyle(usualButtonStyle);
            }
        }
        if (p == "FOCUSED") {
            if (getSkinnable().isFocused() && getSkinnable().isDefaultButton()) {
                setFocusBorder();
            } else if (getSkinnable().isFocused()) {
                setFocusBorder();
            } else if (!getSkinnable().isFocused()
                    || getSkinnable().isDisable()) {
                setDropShadow();
            }
        }
        if (p == "DEFAULT_BUTTON") {
            setDefaultButtonAnimation();
        }
        if (p == "DISABLED") {
            if (getSkinnable().isDefaultButton()) {
                if (getSkinnable().isDisabled()
                        && defaultButtonTransition != null
                        && defaultButtonTransition.getStatus() != Status.RUNNING) {
                    defaultButtonTransition.stop();
                } else {
                    setDefaultButtonAnimation();
                }
            }
        }
    }

    private void setDefaultButtonAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (defaultButtonTransition != null
                    && defaultButtonTransition.getStatus() == Status.RUNNING) {
                defaultButtonTransition.stop();
            } else {
                final Duration duration = Duration.millis(500);
                defaultButtonTransition = new BindableTransition(duration);
                defaultButtonTransition.setCycleCount(Timeline.INDEFINITE);
                defaultButtonTransition.setAutoReverse(true);

                // The gradient
                final Color startColor1 = Color.rgb(183, 206, 238);
                final Color startColor2 = Color.rgb(142, 188, 237);
                final Color startColor3 = Color.rgb(114, 174, 236);
                final Color startColor4 = Color.rgb(178, 218, 242);

                final Color endColor1 = Color.rgb(203, 243, 254);
                final Color endColor2 = Color.rgb(166, 211, 248);
                final Color endColor3 = Color.rgb(137, 198, 248);
                final Color endColor4 = Color.rgb(203, 243, 254);

                defaultButtonTransition.fractionProperty().addListener(
                        new ChangeListener<Number>() {

                            @Override
                            public void changed(
                                    ObservableValue<? extends Number> observable,
                                    Number oldValue, Number newValue) {

                                List<BackgroundFill> list = new ArrayList<>();

                                // the animated fill
                                list.add(BackgroundFillBuilder
                                        .create()
                                        .fill(LinearGradientBuilder
                                                .create()
                                                .startX(1.0)
                                                .stops(new Stop(
                                                        0f,
                                                        Color.color(
                                                                (endColor1
                                                                        .getRed() - startColor1
                                                                        .getRed())
                                                                        * newValue
                                                                                .doubleValue()
                                                                        + startColor1
                                                                                .getRed(),
                                                                (endColor1
                                                                        .getGreen() - startColor1
                                                                        .getGreen())
                                                                        * newValue
                                                                                .doubleValue()
                                                                        + startColor1
                                                                                .getGreen(),
                                                                (endColor1
                                                                        .getBlue() - startColor1
                                                                        .getBlue())
                                                                        * newValue
                                                                                .doubleValue()
                                                                        + startColor1
                                                                                .getBlue())),
                                                        new Stop(
                                                                0.5f,
                                                                Color.color(
                                                                        (endColor2
                                                                                .getRed() - startColor2
                                                                                .getRed())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor2
                                                                                        .getRed(),
                                                                        (endColor2
                                                                                .getGreen() - startColor2
                                                                                .getGreen())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor2
                                                                                        .getGreen(),
                                                                        (endColor2
                                                                                .getBlue() - startColor2
                                                                                .getBlue())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor2
                                                                                        .getBlue())),
                                                        new Stop(
                                                                0.51f,
                                                                Color.color(
                                                                        (endColor3
                                                                                .getRed() - startColor3
                                                                                .getRed())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor3
                                                                                        .getRed(),
                                                                        (endColor3
                                                                                .getGreen() - startColor3
                                                                                .getGreen())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor3
                                                                                        .getGreen(),
                                                                        (endColor3
                                                                                .getBlue() - startColor3
                                                                                .getBlue())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor3
                                                                                        .getBlue())),
                                                        new Stop(
                                                                1f,
                                                                Color.color(
                                                                        (endColor4
                                                                                .getRed() - startColor4
                                                                                .getRed())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor4
                                                                                        .getRed(),
                                                                        (endColor4
                                                                                .getGreen() - startColor4
                                                                                .getGreen())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor4
                                                                                        .getGreen(),
                                                                        (endColor4
                                                                                .getBlue() - startColor4
                                                                                .getBlue())
                                                                                * newValue
                                                                                        .doubleValue()
                                                                                + startColor4
                                                                                        .getBlue())))
                                                .build())
                                        .radii(new CornerRadii(4.0)).build());

                                getSkinnable().setBackground(
                                        new Background(list.get(0)));
                            }

                        });

                defaultButtonTransition.play();
            }
        }
    }
}