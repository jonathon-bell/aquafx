package de.zillmann.javafx.aqua.controls.skin;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundFillBuilder;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import com.sun.javafx.scene.control.skin.ProgressBarSkin;

import de.zillmann.javafx.aqua.AquaFx;
import de.zillmann.javafx.aqua.util.BindableTransition;

public class AquaProgressBarSkin extends ProgressBarSkin {

    private BindableTransition indeterminateProgressTransition;
    private BindableTransition determinateProgressTransition;
    private final Image image = new Image(AquaFx.class.getResource("controls/skin/progress.png").toExternalForm());
    private final Background originalBackground = getSkinnable().getBackground();

    public AquaProgressBarSkin(ProgressBar progressBar) {
        super(progressBar);
        if (progressBar.isIndeterminate()) {
            setIndeterminateProgressAnimation();
        } else {
            setDeterminateProgressAnimation();
        }
        registerChangeListener(progressBar.disabledProperty(), "DISABLED");
        registerChangeListener(progressBar.indeterminateProperty(), "INDETERMINATE");
    }

    @Override protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);
        if (propertyReference == "DISABLED") {
            if (getSkinnable().isIndeterminate()) {
                if (getSkinnable().isDisabled() && indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                    indeterminateProgressTransition.stop();
                } else {
                    setIndeterminateProgressAnimation();
                }
            } else {
                if (getSkinnable().isDisabled() && determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                    determinateProgressTransition.stop();
                } else {
                    setDeterminateProgressAnimation();
                }
            }
        }
        if (propertyReference == "INDETERMINATE") {
            if (getSkinnable().isIndeterminate()) {
                if (determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                    determinateProgressTransition.stop();
                }
                ((StackPane) getSkinnable().lookup(".bar")).setBackground(null);
                setIndeterminateProgressAnimation();
            } else {
                if (indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                    indeterminateProgressTransition.stop();
                }
                getSkinnable().setBackground(originalBackground);
                setDeterminateProgressAnimation();
            }
        }
    }

    private void setIndeterminateProgressAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                indeterminateProgressTransition.stop();
            } else {
                final Duration duration = Duration.millis(2000);
                indeterminateProgressTransition = new BindableTransition(duration);
                indeterminateProgressTransition.setCycleCount(Timeline.INDEFINITE);
                indeterminateProgressTransition.setAutoReverse(false);
                indeterminateProgressTransition.setInterpolator(Interpolator.LINEAR);
                indeterminateProgressTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        double startX = newValue.doubleValue() * 0.82;
                        double endX = newValue.doubleValue() * 0.82 + 0.05d;

                        List<BackgroundFill> list = new ArrayList<>();
                        list.add(BackgroundFillBuilder.create().fill(
                                LinearGradientBuilder.create().startX(1.0).stops(new Stop(0.0f, Color.rgb(176, 176, 176)),
                                        new Stop(1f, Color.rgb(207, 207, 207))).build()).radii(new CornerRadii(2.0)).build());
                        // the animated fill
                        list.add(BackgroundFillBuilder.create().fill(
                                LinearGradientBuilder.create().startX(startX).startY(0.45).endX(endX).endY(0.0).cycleMethod(
                                        CycleMethod.REFLECT).stops(new Stop(0.5f, Color.rgb(84, 169, 239)),
                                        new Stop(0.5f, Color.rgb(236, 236, 236))).build()).radii(new CornerRadii(2.0)).insets(
                                new Insets(1)).build());
                        list.add(BackgroundFillBuilder.create().fill(
                                LinearGradientBuilder.create().startX(1.0).stops(new Stop(0.05f, Color.rgb(255, 255, 255, 0.7)),
                                        new Stop(0.05f, Color.rgb(255, 255, 255, 0.55)),
                                        new Stop(0.5f, Color.rgb(255, 255, 255, 0.1)),
                                        new Stop(0.5f, Color.rgb(255, 255, 255, 0.0)),
                                        new Stop(0.6f, Color.rgb(255, 255, 255, 0.0)),
                                        new Stop(0.85f, Color.rgb(255, 255, 255, 0.4)),
                                        new Stop(1f, Color.rgb(245, 245, 245, 0.7))).build()).radii(new CornerRadii(2.0)).insets(
                                new Insets(1)).build());
                        getSkinnable().setBackground(new Background(list, null));
                    }
                });
                indeterminateProgressTransition.play();
            }
        } else {
            List<BackgroundFill> list = new ArrayList<>();
            list.add(BackgroundFillBuilder.create().fill(
                    LinearGradientBuilder.create().startX(1.0).stops(new Stop(0.0f, Color.rgb(176, 176, 176)),
                            new Stop(1f, Color.rgb(207, 207, 207))).build()).radii(new CornerRadii(2.0)).build());
            list.add(BackgroundFillBuilder.create().fill(
                    LinearGradientBuilder.create().startX(0).startY(0.45).endX(0.05).endY(0.0).cycleMethod(CycleMethod.REFLECT).stops(
                            new Stop(0.5f, Color.rgb(84, 169, 239)), new Stop(0.5f, Color.rgb(236, 236, 236))).build()).radii(
                    new CornerRadii(2.0)).insets(new Insets(1)).build());
            list.add(BackgroundFillBuilder.create().fill(
                    LinearGradientBuilder.create().startX(1.0).stops(new Stop(0.05f, Color.rgb(255, 255, 255, 0.7)),
                            new Stop(0.05f, Color.rgb(255, 255, 255, 0.55)), new Stop(0.5f, Color.rgb(255, 255, 255, 0.1)),
                            new Stop(0.5f, Color.rgb(255, 255, 255, 0.0)), new Stop(0.6f, Color.rgb(255, 255, 255, 0.0)),
                            new Stop(0.85f, Color.rgb(255, 255, 255, 0.4)), new Stop(1f, Color.rgb(245, 245, 245, 0.7))).build()).radii(
                    new CornerRadii(2.0)).insets(new Insets(1)).build());
            getSkinnable().setBackground(new Background(list, null));
        }
    }

    private void setDeterminateProgressAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                determinateProgressTransition.stop();
            } else {
                final Duration duration = Duration.millis(1000);
                determinateProgressTransition = new BindableTransition(duration);
                determinateProgressTransition.setCycleCount(Timeline.INDEFINITE);
                determinateProgressTransition.setAutoReverse(false);
                determinateProgressTransition.setInterpolator(Interpolator.LINEAR);
                determinateProgressTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        StackPane bar = ((StackPane) getSkinnable().lookup(".bar"));
                        int start = 1 - (int) ((38 * bar.getHeight() / 20) * newValue.doubleValue());
                        ImagePattern pattern = new ImagePattern(image, start, 0, (38 * bar.getHeight() / 20), bar.getHeight(), false);

                        bar.setBackground(new Background(BackgroundFillBuilder.create().fill(pattern).build()));
                    }
                });
                determinateProgressTransition.play();
            }
        } else {
            StackPane bar = ((StackPane) getSkinnable().lookup(".bar"));
            ImagePattern pattern = new ImagePattern(image, 0, 0, (38 * bar.getHeight() / 20), bar.getHeight(), false);
            bar.setBackground(new Background(BackgroundFillBuilder.create().fill(pattern).build()));
        }
    }

}