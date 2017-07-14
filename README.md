# ZeroMQ SouthBound Provider

Aim of this project is to act as a ZMQ Router encapsualted inside ONOS Device Provider, so that multiple ZMQ clients can communicate to ONOS via ZMQ Southbound.

Please refer Wiki for installation & usage.

# For Installation with BUCK:

Option 1:

	Add in modules.defs in the ONOS parent directory inside the ONOS_APPS list this line:
	
		'//apps/zeromqprovider:onos-apps-zeromqprovider-oar',

Option 2:

	Run buck build to generate the oar file in the buck-out directory:
	
		buck build //apps/zeromqprovider:onos-apps-zeromqprovider-oar
		
	Install application with onos-app from inside the buck-out:
	
		onos-app localhost install buck-out/gen/apps/zeromqprovider/onos-apps-zeromqprovider-oar/app.oar

# JMeter

Load the .jmx file and don't forget to put in the Testplan's classpath the zmqClient.jar

TODO: running the zmqClient from inside JMeter hangs the thread and needs a restart
