/**
 * Copyright (c) 2013, Claudine Zillmann
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of AquaFX, the website aquafx-project.com, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CLAUDINE ZILLMANN BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.aquafx_project.controls.skin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import com.aquafx_project.controls.skin.effects.FocusBorder;
import com.aquafx_project.controls.skin.effects.Shadow;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;


public class AquaComboBoxListViewSkin<T> extends ComboBoxListViewSkin<T> implements AquaSkin {

    public AquaComboBoxListViewSkin(ComboBox<T> comboBox) {
        super(comboBox);
        registerChangeListener(comboBox.disabledProperty(), "DISABLED");

        if (getSkinnable().isFocused()) {
            setFocusBorder();
        } else if(!getSkinnable().isFocused() && !getSkinnable().isDisabled()) {
            setDropShadow();
        } 
        
        for (Object child : getChildren()) {
            ((Node) child).focusedProperty().addListener(focusListener);
        }
        if (comboBox.isEditable()) {
            getDisplayNode().focusedProperty().addListener(focusListener);
        }
        getSkinnable().focusedProperty().addListener(focusListener);
    }

    private ChangeListener<Boolean> focusListener = new ChangeListener<Boolean>() {

        @Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                setFocusBorder();
            } else {
                setDropShadow();
            }
        }
    };

    private void setFocusBorder() {
        getSkinnable().setEffect(new FocusBorder());
        getListView().setEffect(null);
    }

    private void setDropShadow() {
        getSkinnable().setEffect(new Shadow(false));
    }
    
    @Override protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if (p == "DISABLED") {
            if (getSkinnable().isDisabled()) {
                getSkinnable().setEffect(null);
            } else {
                setDropShadow();
            }
        }
    }
}
