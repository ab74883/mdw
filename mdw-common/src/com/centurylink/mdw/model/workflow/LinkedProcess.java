/*
 * Copyright (C) 2017 CenturyLink, Inc.
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
package com.centurylink.mdw.model.workflow;

import com.centurylink.mdw.model.Jsonable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a workflow process in a hierarchical structure of called/calling processes.
 */
public class LinkedProcess implements Jsonable {

    private Process process;
    public Process getProcess() { return process; }

    private LinkedProcess parent;
    public LinkedProcess getParent() { return parent; }
    public void setParent(LinkedProcess parent) { this.parent = parent; }

    private List<LinkedProcess> children = new ArrayList<>();
    public List<LinkedProcess> getChildren() { return children; }
    public void setChildren(List<LinkedProcess> children) { this.children = children; }

    public LinkedProcess(Process process) {
        this.process = process;
    }

    public LinkedProcess(JSONObject jsonObj) throws JSONException {
        JSONObject procObj = jsonObj.getJSONObject("process");
        this.process = new Process(procObj);
        if (jsonObj.has("children")) {
            JSONArray childrenArr = jsonObj.getJSONArray("children");
            for (int i = 0; i < childrenArr.length(); i++) {
                JSONObject childObj = (JSONObject)childrenArr.get(i);
                this.children.add(new LinkedProcess(childObj));
            }
        }
    }

    /**
     * Includes children (not parent).
     */
    public JSONObject getJson() throws JSONException {
        JSONObject json = create();
        json.put("process", process.getSummaryJson());
        if (!children.isEmpty()) {
            JSONArray childrenArr = new JSONArray();
            for (LinkedProcess child : children) {
                childrenArr.put(child.getJson());
            }
            json.put("children", childrenArr);
        }
        return json;
    }

    public String getJsonName() {
        return "LinkedProcess";
    }

    @Override
    public String toString() {
        return process.getFullLabel();
    }
}
