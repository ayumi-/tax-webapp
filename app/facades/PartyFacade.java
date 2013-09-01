package facades;

import models.party.Party;

public class PartyFacade {
	
	private Party party;

	public PartyFacade(String partyName) {
		this.party = new Party(partyName);
	}

	public static Party find(long id) {
		return Party.find(id);
	}

	public Party save() {
		return Party.save(party);
	}

}
