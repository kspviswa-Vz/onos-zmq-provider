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

package com.vz.onosproject.BlobStore;

import org.onosproject.net.DeviceId;

import java.util.ArrayList;
import java.util.List;

public class DeviceBlob {

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(DeviceId deviceId) {
        this.deviceId = deviceId;
    }

    public void addBlob(Blob blob) {
        blobs.add(blob);
    }

    public void removeBlob(int index) {
        blobs.remove(index);
    }

    public void removeBlobLast() {
        blobs.remove(-1);
    }

    public List<Blob> getBlobs() {
        return blobs;
    }

    DeviceId deviceId;
    List<Blob> blobs = new ArrayList<Blob>();
}
