package facades;

import models.party.Party;

public class PartyFacade {
	
	private Party party;

	public PartyFacade(String partyName) {
		this.party = new Party(partyName);
	}

	public Party getParty() {
		return party;
	}
}
