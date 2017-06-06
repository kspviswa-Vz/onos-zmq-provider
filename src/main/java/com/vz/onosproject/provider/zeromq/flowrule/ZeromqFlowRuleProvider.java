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

package com.vz.onosproject.provider.zeromq.flowrule;

import com.vz.onosproject.provider.zeromq.api.AbstractZeromqProvider;
import com.vz.onosproject.provider.zeromq.api.ZeromqSBController;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flow.FlowRuleBatchOperation;
import org.onosproject.net.flow.FlowRuleProvider;

public class ZeromqFlowRuleProvider extends AbstractZeromqProvider
        implements FlowRuleProvider {

    //@Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    //protected ZeromqSBController controller;

    @Override
    public void applyFlowRule(FlowRule... flowRules) {

    }

    @Override
    public void removeFlowRule(FlowRule... flowRules) {

    }

    @Override
    public void removeRulesById(ApplicationId applicationId, FlowRule... flowRules) {

    }

    @Override
    public void executeBatch(FlowRuleBatchOperation flowRuleBatchOperation) {

    }
}
