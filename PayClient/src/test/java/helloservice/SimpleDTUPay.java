package helloservice;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Payment;

import java.util.List;

public class SimpleDTUPay {

	WebTarget baseUrl;

	public SimpleDTUPay() {
		Client client = ClientBuilder.newClient();
		baseUrl = client.target("http://localhost:8080/");
	}

	public List<Payment> getPayments() {
		return baseUrl.path("payments")
				.request()
				.get(new GenericType<List<Payment>>(){});
	}
	
	public boolean pay(int amount, String cid, String mid) {
		Payment p = new Payment(amount, cid, mid);
		Response r = baseUrl.path("payments")
				.request()
				.post(Entity.entity(p, MediaType.APPLICATION_JSON));

		if(r.getStatus() == 201) {
			return true;
		}

		// Throw exception with error message
		throw new IllegalArgumentException(r.readEntity(String.class));
	}
}
