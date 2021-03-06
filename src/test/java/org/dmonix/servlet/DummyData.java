/**
 * Copyright 2016 Peter Nerg
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dmonix.servlet;

/**
 * Dummy data to use for JSon testing
 * @author Peter Nerg
 */
public class DummyData {
    public final String name;
    public final int id;

    public DummyData(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name+":"+id;
    }
}
