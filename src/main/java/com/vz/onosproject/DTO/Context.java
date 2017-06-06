
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
    "instructions",
    "context-id",
    "dpn-group",
    "delegating-ip-prefixes",
    "ul",
    "dl",
    "dpns",
    "imsi",
    "ebi",
    "lbi"
})
public class Context implements Serializable
{

    @JsonProperty("instructions")
    private Instructions instructions;
    @JsonProperty("context-id")
    private String contextId;
    @JsonProperty("dpn-group")
    private String dpnGroup;
    @JsonProperty("delegating-ip-prefixes")
    private List<String> delegatingIpPrefixes = null;
    @JsonProperty("ul")
    private Ul ul;
    @JsonProperty("dl")
    private Dl dl;
    @JsonProperty("dpns")
    private List<Dpn> dpns = null;
    @JsonProperty("imsi")
    private String imsi;
    @JsonProperty("ebi")
    private String ebi;
    @JsonProperty("lbi")
    private String lbi;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 2382051114238034333L;

    @JsonProperty("instructions")
    public Instructions getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(Instructions instructions) {
        this.instructions = instructions;
    }

    @JsonProperty("context-id")
    public String getContextId() {
        return contextId;
    }

    @JsonProperty("context-id")
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    @JsonProperty("dpn-group")
    public String getDpnGroup() {
        return dpnGroup;
    }

    @JsonProperty("dpn-group")
    public void setDpnGroup(String dpnGroup) {
        this.dpnGroup = dpnGroup;
    }

    @JsonProperty("delegating-ip-prefixes")
    public List<String> getDelegatingIpPrefixes() {
        return delegatingIpPrefixes;
    }

    @JsonProperty("delegating-ip-prefixes")
    public void setDelegatingIpPrefixes(List<String> delegatingIpPrefixes) {
        this.delegatingIpPrefixes = delegatingIpPrefixes;
    }

    @JsonProperty("ul")
    public Ul getUl() {
        return ul;
    }

    @JsonProperty("ul")
    public void setUl(Ul ul) {
        this.ul = ul;
    }

    @JsonProperty("dl")
    public Dl getDl() {
        return dl;
    }

    @JsonProperty("dl")
    public void setDl(Dl dl) {
        this.dl = dl;
    }

    @JsonProperty("dpns")
    public List<Dpn> getDpns() {
        return dpns;
    }

    @JsonProperty("dpns")
    public void setDpns(List<Dpn> dpns) {
        this.dpns = dpns;
    }

    @JsonProperty("imsi")
    public String getImsi() {
        return imsi;
    }

    @JsonProperty("imsi")
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @JsonProperty("ebi")
    public String getEbi() {
        return ebi;
    }

    @JsonProperty("ebi")
    public void setEbi(String ebi) {
        this.ebi = ebi;
    }

    @JsonProperty("lbi")
    public String getLbi() {
        return lbi;
    }

    @JsonProperty("lbi")
    public void setLbi(String lbi) {
        this.lbi = lbi;
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
