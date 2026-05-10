public interface GameEvent {
    void apply(Company company);
}

public class MarketSlowdownEvent implements GameEvent {

    public void apply(Company company) {
        company.reduceCash(5000);
    }
}