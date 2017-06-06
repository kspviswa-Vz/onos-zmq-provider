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

package com.vz.onosproject.provider.zeromq.api;

import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceProviderService;

import com.vz.onosproject.BlobStore.Blob;
import java.util.List;

public interface ZeromqSBController {

    public void initConnections(DeviceProviderService providerService);

    public void destroyConnections();

    public void writeToDevice(DeviceId deviceId, Blob blob);

    public void monitorConnections();

    public List<String> getAvailableDevices();
}
