package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public class MarketSlowdownEvent implements GameEvent {

    public void apply(Company company) {
        System.out.println("\n[!] ZDARZENIE LOSOWE [!]");
        System.out.println("Spowolnienie rynku!");
        company.reduceCash(5000);
    }
}