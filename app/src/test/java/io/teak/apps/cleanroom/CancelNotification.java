/* Teak -- Copyright (C) 2017 GoCarrot Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.teak.apps.cleanroom;

import org.junit.Test;

import static org.junit.Assert.*;

import io.teak.sdk.TeakNotification;

public class CancelNotification {
    @Test
    public void scheduleId_null() throws Exception {
        assertNull(TeakNotification.cancelNotification(null));
    }
}
