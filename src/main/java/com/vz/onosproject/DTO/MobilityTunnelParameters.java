
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
    "tunnel-type",
    "tunnel-identifier"
})
public class MobilityTunnelParameters implements Serializable
{

    @JsonProperty("tunnel-type")
    private String tunnelType;
    @JsonProperty("tunnel-identifier")
    private String tunnelIdentifier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 2319123196218599174L;

    @JsonProperty("tunnel-type")
    public String getTunnelType() {
        return tunnelType;
    }

    @JsonProperty("tunnel-type")
    public void setTunnelType(String tunnelType) {
        this.tunnelType = tunnelType;
    }

    @JsonProperty("tunnel-identifier")
    public String getTunnelIdentifier() {
        return tunnelIdentifier;
    }

    @JsonProperty("tunnel-identifier")
    public void setTunnelIdentifier(String tunnelIdentifier) {
        this.tunnelIdentifier = tunnelIdentifier;
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
