/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.journal.ui;

import org.terasology.journal.JournalManager;
import org.terasology.math.Rect2i;
import org.terasology.math.Vector2i;
import org.terasology.rendering.nui.Canvas;
import org.terasology.rendering.nui.itemRendering.ItemRenderer;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class JournalChapterRenderer implements ItemRenderer<JournalManager.JournalChapter> {
    @Override
    public void draw(JournalManager.JournalChapter value, Canvas canvas) {
        draw(value, canvas, canvas.getRegion());
    }

    @Override
    public void draw(JournalManager.JournalChapter value, Canvas canvas, Rect2i subregion) {
        canvas.drawTexture(value.getTexture(), subregion);
    }

    @Override
    public Vector2i getPreferredSize(JournalManager.JournalChapter value, Canvas canvas) {
        return new Vector2i(50, 50);
    }

    @Override
    public String getTooltip(JournalManager.JournalChapter value) {
        return value.getChapterName();
    }
}
