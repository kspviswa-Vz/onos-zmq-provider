
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
    "tunnel-local-address",
    "tunnel-remote-address",
    "mobility-tunnel-parameters",
    "dpn-parameters"
})
public class Ul implements Serializable
{

    @JsonProperty("tunnel-local-address")
    private String tunnelLocalAddress;
    @JsonProperty("tunnel-remote-address")
    private String tunnelRemoteAddress;
    @JsonProperty("mobility-tunnel-parameters")
    private MobilityTunnelParameters mobilityTunnelParameters;
    @JsonProperty("dpn-parameters")
    private DpnParameters dpnParameters;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3080013571958381374L;

    @JsonProperty("tunnel-local-address")
    public String getTunnelLocalAddress() {
        return tunnelLocalAddress;
    }

    @JsonProperty("tunnel-local-address")
    public void setTunnelLocalAddress(String tunnelLocalAddress) {
        this.tunnelLocalAddress = tunnelLocalAddress;
    }

    @JsonProperty("tunnel-remote-address")
    public String getTunnelRemoteAddress() {
        return tunnelRemoteAddress;
    }

    @JsonProperty("tunnel-remote-address")
    public void setTunnelRemoteAddress(String tunnelRemoteAddress) {
        this.tunnelRemoteAddress = tunnelRemoteAddress;
    }

    @JsonProperty("mobility-tunnel-parameters")
    public MobilityTunnelParameters getMobilityTunnelParameters() {
        return mobilityTunnelParameters;
    }

    @JsonProperty("mobility-tunnel-parameters")
    public void setMobilityTunnelParameters(MobilityTunnelParameters mobilityTunnelParameters) {
        this.mobilityTunnelParameters = mobilityTunnelParameters;
    }

    @JsonProperty("dpn-parameters")
    public DpnParameters getDpnParameters() {
        return dpnParameters;
    }

    @JsonProperty("dpn-parameters")
    public void setDpnParameters(DpnParameters dpnParameters) {
        this.dpnParameters = dpnParameters;
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
