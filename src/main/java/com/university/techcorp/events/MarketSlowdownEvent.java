package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public class MarketSlowdownEvent implements GameEvent {

    public void apply(Company company) {
        company.reduceCash(5000);
    }
}