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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.net.DeviceId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Component
public class BlobStoreImpl implements BlobStore {

    Map<DeviceId, DeviceBlob> blobmap = new ConcurrentHashMap<>();

    @Override
    public void InsertBlob(DeviceId device, Blob blob) {
        DeviceBlob dblob = blobmap.getOrDefault(device, null);
        if(dblob != null) {
            dblob.addBlob(blob);
        }
        else
        {
            dblob = new DeviceBlob();
            dblob.setDeviceId(device);
            dblob.addBlob(blob);
            blobmap.putIfAbsent(device, dblob);
        }
    }

    @Override
    public void RemoveBlob(DeviceId device) {
        DeviceBlob dblob = blobmap.getOrDefault(device, null);
        if(dblob != null) {
            dblob.removeBlobLast();
        }
    }

    @Override
    public List<Blob> getBlobs(DeviceId device) {
        DeviceBlob dblob = blobmap.getOrDefault(device, null);
        if (dblob != null) {
            return dblob.getBlobs();
        }
        return null;
    }
}
