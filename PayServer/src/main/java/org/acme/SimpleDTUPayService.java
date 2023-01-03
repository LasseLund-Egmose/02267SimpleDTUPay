package org.acme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDTUPayService {

    private String[] customers = new String[] {"cid1"};
    private String[] merchants = new String[] {"mid1"};
    private ArrayList<Payment> payments = new ArrayList<>();

    public List<Payment> getPayments() {
        return this.payments;
    }

    public int pay(Payment p) {
        if(Arrays.stream(customers).noneMatch(c -> c.equals(p.getCid()))) {
            throw new IllegalArgumentException("customer with id " + p.getCid() + " is unknown");
        }

        if(Arrays.stream(merchants).noneMatch(c -> c.equals(p.getMid()))) {
            throw new IllegalArgumentException("merchant with id " + p.getMid() + " is unknown");
        }

        payments.add(p);
        return payments.indexOf(p);
    }

}
