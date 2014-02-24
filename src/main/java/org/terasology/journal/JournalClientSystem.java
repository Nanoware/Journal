/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.journal;

import org.terasology.engine.Time;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;
import org.terasology.input.ButtonState;
import org.terasology.journal.ui.JournalNUIWindow;
import org.terasology.journal.ui.NewEntryWindow;
import org.terasology.network.ClientComponent;
import org.terasology.registry.In;
import org.terasology.rendering.nui.NUIManager;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
@RegisterSystem(RegisterMode.CLIENT)
public class JournalClientSystem extends BaseComponentSystem implements UpdateSubscriberSystem {
    @In
    private NUIManager nuiManager;
    @In
    private Time time;

    private final static long FULL_ALPHA_TIME = 3000;
    private final static long DIM_ALPHA_TIME = 1500;

    private long lastNotificationReceived;

    @Override
    public void update(float delta) {
        long currentTime = time.getGameTimeInMs();
        float alpha = (currentTime > lastNotificationReceived + FULL_ALPHA_TIME + DIM_ALPHA_TIME) ? 0f :
                currentTime > lastNotificationReceived + FULL_ALPHA_TIME ? 1f - ((currentTime - lastNotificationReceived - FULL_ALPHA_TIME) / (1f * DIM_ALPHA_TIME)) : 1f;

        NewEntryWindow window = ((NewEntryWindow) nuiManager.getScreen("Journal:NewEntryWindow"));
        if (alpha == 0f && window != null) {
            nuiManager.closeScreen(window);
        } else if (alpha > 0f) {
            if (window == null) {
                window = (NewEntryWindow) nuiManager.pushScreen("Journal:NewEntryWindow");
            }
            window.setAlpha(alpha);
        }
    }

    @ReceiveEvent(components = ClientComponent.class)
    public void openJournal(JournalButton event, EntityRef character) {
        if (event.getState() == ButtonState.DOWN) {
            nuiManager.toggleScreen("Journal:JournalWindow");
            JournalNUIWindow window = (JournalNUIWindow) nuiManager.getScreen("Journal:JournalWindow");
            window.refreshJournal();
        }
    }

    @ReceiveEvent
    public void newEntryNotificationReceived(NewJournalEntryDiscoveredEvent event, EntityRef character) {
        lastNotificationReceived = time.getGameTimeInMs();
    }
}
