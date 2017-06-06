/*
 * Copyright 2016-present Open Networking Laboratory
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
package com.vz.onosproject.zeromqprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vz.onosproject.BlobStore.Blob;
import com.vz.onosproject.BlobStore.BlobStore;
import com.vz.onosproject.Builder.FpcBuilder;
import com.vz.onosproject.DTO.DpnParameters;
import com.vz.onosproject.DTO.FpcDTO;
import com.vz.onosproject.DTO.Instructions;
import com.vz.onosproject.DTO.Payload;
import com.vz.onosproject.DTO.Input;
import com.vz.onosproject.DTO.*;
import com.vz.onosproject.ZMQAppComponent;
import com.vz.onosproject.provider.zeromq.api.ZeromqSBController;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onlab.util.KryoNamespace;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.store.serializers.KryoNamespaces;
import org.onosproject.net.DeviceId;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.rest.AbstractWebResource;
import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.Serializer;
import org.onosproject.store.service.StorageService;
import org.onosproject.store.service.Versioned;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import static org.onlab.util.Tools.nullIsNotFound;

/**
 * Sample web resource.
 */
@Path("/zmq/")
public class AppWebResource extends AbstractWebResource {

    private static final Logger log =
            LoggerFactory.getLogger(AppWebResource.class);

    protected BlobStore store = get(BlobStore.class);

    private ConsistentMap<DeviceId, Blob> blobSet;
    private ConsistentMap<String, FpcDTO> fpcSet;
    private ZeromqSBController controller = get(ZeromqSBController.class);

    protected StorageService storageService = get(StorageService.class);
    protected CoreService coreService = get(CoreService.class);

    private final String INVALID_DEVICEID = "No such device available";
    private final String INVALID_FLOW = "Malformed flow payload";

    public enum FpcMsgType {
        SESSION_UPLINK,
        DOWNLINK,
        DELETE,
        INVALID_TYPE;
    }

    public static long getNumPostRecieved() {
        return numPostRecieved;
    }

    private static long numPostRecieved;

    public AppWebResource() {
        ApplicationId appId = coreService.getAppId("com.vz.onosproject.zeromqprovider");

        KryoNamespace.Builder serializer = KryoNamespace.newBuilder()
                .register(KryoNamespaces.API)
                .register(FpcDTO.class)
                .register(Payload.class)
                .register(Input.class)
                .register(Payload.class)
                .register(Instructions.class)
                .register(Context.class)
                .register(Dl.class)
                .register(Ul.class)
                .register(Dpn.class)
                .register(DpnParameters.class)
                .register(DpnParameters_.class)
                .register(DpnParameters__.class)
                .register(MobilityTunnelParameters.class)
                .register(MobilityTunnelParameters_.class);

        fpcSet = storageService.<String, FpcDTO>consistentMapBuilder()
                .withSerializer(Serializer.using(serializer.build()))
                .withName("fpc-rule-set")
                .withApplicationId(appId)
                .withPurgeOnUninstall()
                .build();

    }

    public static void incrPostCount() {
        numPostRecieved++;
    }
    /**
     * Get hello world greeting.
     *
     * @return 200 OK
     */
    @GET
    @Path("devices")
    public Response getAllDevices() {
        ObjectNode root = mapper().createObjectNode();
        ArrayNode devNode = root.putArray("ZMQ Devices");

        log.info("###### Getting all zmq devices known to this provider");
        List<String> devices = controller.getAvailableDevices();

        if(devices == null) {
            devNode.add("No devices");
        }
        else {
            for(String d : devices) {
                devNode.add(d);
            }
        }
        return ok(root).build();
    }

    /**
     * Check for instruction type
     */

    FpcMsgType checkAndReturnInstructionType(FpcDTO obj) {

        switch(obj.getPayload().getInput().getContexts().get(0).getInstructions().getInstr3gppMob()) {
            case "session uplink":
                return FpcMsgType.SESSION_UPLINK;
            case "downlink":
                return FpcMsgType.DOWNLINK;
            default:
                return FpcMsgType.INVALID_TYPE;
        }
    }

    boolean isDeleteMsg(String obj) {
       if(obj.contains("targets")) {
           return true;
       }

       return false;
    }

    /**
     * Installs flows to downstream ZMQ device
     * @param stream blob flowrule
     * @return 200 OK
     */
    @POST
    @Path("flows")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response persisFlow(InputStream stream) {
        log.info("#### Pushing a flow");
        ObjectNode jsonTree = null, jsonTree2 = null;
        List<String> devices = controller.getAvailableDevices();
        try {
            jsonTree = (ObjectNode) mapper().readTree(stream);
            jsonTree2 = jsonTree;

            /**
             * New FPC Parser code
             */

            //UplinkFPCdto dto = codec(UplinkFPCdto.class).decode(jsonTree2, this);
            //log.info("##### Text value 1 " + jsonTree.asText());
            //log.info("##### Text value 2 " + jsonTree.toString());
            //log.info("##### Text value 3 " + jsonTree.textValue());

            FpcDTO dto = mapper().readValue(jsonTree.toString(), FpcDTO.class);
            //log.info(" #### Data from codec :  ");
            //log.info("Device Id => " + dto.getDeviceId());
            //log.info("Payload => " + dto.getPayload());

            JsonNode devId = jsonTree.get("DeviceId");
            JsonNode payload = jsonTree.get("Payload");
            String sPayload = payload.toString();

            //log.info("Device Id" + devId.asText());
            //log.info("Payload Text value " + payload.textValue() + " toString " + payload.toString() +"Type " + payload.getNodeType().toString());

            if (devId == null || devId.asText().isEmpty() ||
                    devices.contains(devId.asText()) == false) {
                throw new IllegalArgumentException(INVALID_DEVICEID);
            }

            if (payload == null || sPayload.isEmpty()) {
                throw new IllegalArgumentException(INVALID_FLOW);
            }

            DeviceId deviceId = DeviceId.deviceId(devId.asText());
            Blob blob = new Blob(sPayload.getBytes());

            store.InsertBlob(deviceId, blob);
            controller.writeToDevice(deviceId, blob);
            incrPostCount();
            log.info("#### Total num of posts :  " + getNumPostRecieved());

            /**
             * FPC Stuff
             */

            // First check for delete as JSON itself
            if(isDeleteMsg(sPayload)) {
                log.info("### It is session delete");
                String imsi = dto.getPayload().getInput().getTargets().get(0).getTarget();
                Versioned<FpcDTO> dto2 = fpcSet.get(imsi);
                if (dto2 != null) {
                    FpcDTO ddto = dto2.value();
                    log.info("Here is a matching ul & dl for incoming imsi");
                    log.info("#######");
                    log.info(ddto.toString());
                } else {
                    throw new IllegalArgumentException("No matching uplink");
                }
            }
            else {
                // If the type is Uplink, go-ahead & put a entry
                switch (checkAndReturnInstructionType(dto)) {
                    case SESSION_UPLINK: {
                        log.info("### It is session uplink");
                        String imsi = dto.getPayload().getInput().getContexts().get(0).getImsi();
                        fpcSet.put(imsi, dto);
                        log.info("##### Saved uplink data for IMSI => " + imsi);
                        Context con = dto.getPayload().getInput().getContexts().get(0);
                        String dpn = con.getDpns().get(0).getDpnId();

                        break;
                    }
                    case DOWNLINK: {
                        log.info("### It is session downlink");
                        String imsi = dto.getPayload().getInput().getContexts().get(0).getImsi();
                        Versioned<FpcDTO> dto2 = fpcSet.get(imsi);
                        if (dto2 != null) {
                            FpcDTO ddto = dto2.value();
                            ddto.getPayload().getInput().getContexts().get(0).
                                    setDl(ddto.getPayload().getInput().getContexts().get(0).getDl());
                        } else {
                            throw new IllegalArgumentException("No matching uplink");
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Invalid Message type");
                }
            }
            return Response.ok().build();
        }
        catch(UnsupportedOperationException usex) {
            log.info("##### Error : " + usex.getMessage());
        }
        catch (/*IO*/Exception e) {
            e.printStackTrace();
            log.info("###### ERROR " + e.getMessage());
        }
            return Response.noContent().build();
    }

    /**
     * Get all flows for a given ZMQ device
     * @param deviceId
     * @return 200 OK
     */

    @GET
    @Path("flows/{deviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getFlowsByDevice(@PathParam("deviceId") String deviceId) {
        ObjectNode root = mapper().createObjectNode();
        ArrayNode flowsNode = root.putArray("Flows");

        log.info("###### Getting flows for device " + deviceId);
        List<String> devices = controller.getAvailableDevices();
        try {
            if (deviceId.isEmpty()|| devices.contains(deviceId) == false) {
                throw new IllegalArgumentException(INVALID_DEVICEID);
            }

            DeviceId device = DeviceId.deviceId(deviceId);
            List<Blob> blobs = store.getBlobs(device);

            if(blobs.isEmpty()) {
                flowsNode.add("No Flows");
            }
            else {
                for (Blob b : blobs) {
                    flowsNode.add(new String(b.getBlob()));
                }
            }

            return Response.ok(root).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    /**
     * Get all flows for all ZMQ devices
     * @return 200 OK
     */

    @GET
    @Path("flows")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getFlows() {
        ObjectNode root = mapper().createObjectNode();
        ArrayNode flowsRootNode = root.putArray("Flows");

        log.info("###### Getting All Flows");
        List<String> devices = controller.getAvailableDevices();
        if(devices == null) {
            flowsRootNode.add("No Flows");
        }
        else {
            try {

                for (String d : devices) {
                    DeviceId device = DeviceId.deviceId(d);
                    ObjectNode devroot = mapper().createObjectNode();
                    ArrayNode devNode = devroot.putArray("Device Flows");
                    List<Blob> blobs = store.getBlobs(device);

                    if (blobs.isEmpty()) {
                        devNode.add("No Flows");
                    } else {
                        for (Blob b : blobs) {
                            devNode.add(new String(b.getBlob()));
                        }
                    }
                    devroot.put("Device ID", device.toString());
                    flowsRootNode.add(devroot);
                }

                return Response.ok(root).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Response.noContent().build();
    }

}
