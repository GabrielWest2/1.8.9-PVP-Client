package customclient.acount;

import org.web3j.eth2.api.BeaconNodeApi;
import org.web3j.eth2.api.BeaconNodeClientFactory;
import org.web3j.eth2.api.BeaconNodeService;
import org.web3j.eth2.api.schema.NamedBlockId;

public class MetamaskLogin {
	public static void login() {
		BeaconNodeService service = new BeaconNodeService("http://...");
		BeaconNodeApi client = BeaconNodeClientFactory.build(service);
		
		client.getBeacon().getBlocks().findById(NamedBlockId.HEAD);
		
	}
}
