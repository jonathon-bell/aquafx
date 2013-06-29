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

package com.aquafx_project.controls.skin.styles.styler;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

import com.aquafx_project.controls.skin.AquaTextFieldSkin;
import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.StyleDefinition;
import com.aquafx_project.controls.skin.styles.TextFieldType;

/**
 * The TextFieldStyler with fluent API to change the default style of a TextField.
 * 
 * @author claudinezillmann
 * 
 */
public class TextFieldStyler extends Styler<TextField> {

    /**
     * TextFieldType of a TextField.
     */
    private TextFieldType type;

    /**
     * Creates a new Instance of TextFieldStyler. This has to be the first invocation on
     * TextFieldStyler.
     * 
     * @return The TextFieldStyler.
     */
    public static TextFieldStyler create() {
        return new TextFieldStyler();
    }

    /**
     * Adds a TextFieldType to the TextField
     * 
     * @param type
     *            The TextFieldType for the TextField.
     * @return the TextFieldStyler with the added TextFieldType.
     */
    public TextFieldStyler setType(TextFieldType type) {
        this.type = type;
        check();
        return this;
    }

    @Override public TextFieldStyler setSizeVariant(ControlSizeVariant sizeVariant) {
        return (TextFieldStyler) super.setSizeVariant(sizeVariant);
    }

    @Override public List<StyleDefinition> getAll() {
        List<StyleDefinition> ret = new ArrayList<>(super.getAll());
        ret.add(sizeVariant);
        ret.add(type);
        return ret;
    }

    @Override public void check() {
        if (type != null && type.equals(TextFieldType.SEARCH) && sizeVariant == ControlSizeVariant.MINI) {
            throw new IllegalStyleCombinationException();
        }
    }

    @Override public void style(final TextField textField) {
        super.style(textField);
        textField.setSkin(new AquaTextFieldSkin(textField));
        if (type != null && type == TextFieldType.SEARCH) {
            Skin<?> skin = textField.getSkin();
            if (skin != null && skin instanceof AquaTextFieldSkin) {
                ((AquaTextFieldSkin) skin).showSearchIconProperty().setValue(true);
            }
        } else if (type != null && type != TextFieldType.SEARCH) {
            Skin<?> skin = textField.getSkin();
            if (skin != null && skin instanceof AquaTextFieldSkin) {
                ((AquaTextFieldSkin) skin).showSearchIconProperty().setValue(false);
            }
        }
    }
}
