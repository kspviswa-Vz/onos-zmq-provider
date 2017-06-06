
/*
 * Copyright 2017 Verizon
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

package com.vz.onosproject.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "op-id",
    "contexts",
    "targets",
    "client-id",
    "session-state",
    "admin-state",
    "op-type",
    "op-ref-scope"
})
public class Input implements Serializable
{

    @JsonProperty("op-id")
    private String opId;
    @JsonProperty("contexts")
    private List<Context> contexts = null;
    @JsonProperty("targets")
    private List<Target> targets = null;
    @JsonProperty("client-id")
    private String clientId;
    @JsonProperty("session-state")
    private String sessionState;
    @JsonProperty("admin-state")
    private String adminState;
    @JsonProperty("op-type")
    private String opType;
    @JsonProperty("op-ref-scope")
    private String opRefScope;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1625893497098808370L;

    @JsonProperty("op-id")
    public String getOpId() {
        return opId;
    }

    @JsonProperty("op-id")
    public void setOpId(String opId) {
        this.opId = opId;
    }

    @JsonProperty("contexts")
    public List<Context> getContexts() {
        return contexts;
    }

    @JsonProperty("contexts")
    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    @JsonProperty("targets")
    public List<Target> getTargets() {
        return targets;
    }

    @JsonProperty("targets")
    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    @JsonProperty("client-id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client-id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("session-state")
    public String getSessionState() {
        return sessionState;
    }

    @JsonProperty("session-state")
    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    @JsonProperty("admin-state")
    public String getAdminState() {
        return adminState;
    }

    @JsonProperty("admin-state")
    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    @JsonProperty("op-type")
    public String getOpType() {
        return opType;
    }

    @JsonProperty("op-type")
    public void setOpType(String opType) {
        this.opType = opType;
    }

    @JsonProperty("op-ref-scope")
    public String getOpRefScope() {
        return opRefScope;
    }

    @JsonProperty("op-ref-scope")
    public void setOpRefScope(String opRefScope) {
        this.opRefScope = opRefScope;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
