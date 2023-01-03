package helloservice;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.Payment;

import java.util.List;

public class SimpleDTUPaySteps {
	String cid, mid;
	SimpleDTUPay dtuPay = new SimpleDTUPay();
	boolean successful;

	String errorMsg;

	List<Payment> payments;

	@Given("a customer with id {string}")
	public void aCustomerWithId(String cid) {
		this.cid = cid;
	}
	@Given("a merchant with id {string}")
	public void aMerchantWithId(String mid) {
		this.mid = mid;
	}
	@When("the merchant initiates a payment for {int} kr by the customer")
	public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
		try {
			dtuPay.pay(amount,cid,mid);
			successful = true;
		} catch (IllegalArgumentException e) {
			successful = false;
			errorMsg = e.getMessage();
		}
	}
	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {
		assertTrue(successful);
	}

	@Given("a successful payment of {int} kr from customer {string} to merchant {string}")
	public void aSuccessfulPaymentOfKrFromCustomerToMerchant(int amount, String cid, String mid) {
		this.aCustomerWithId(cid);
		this.aMerchantWithId(mid);
		this.theMerchantInitiatesAPaymentForKrByTheCustomer(amount);
		this.thePaymentIsSuccessful();
	}

	@When("the manager asks for a list of payments")
	public void theManagerAsksForAListOfPayments() {
		this.payments = dtuPay.getPayments();
	}

	@Then("the list contains a payments where customer {string} paid {int} kr to merchant {string}")
	public void theListContainsAPaymentsWhereCustomerPaidKrToMerchant(String cid, int amount, String mid) {
		boolean contains = this.payments.stream().anyMatch(payment ->
			payment.getCid().equals(cid) &&
			payment.getAmount() == amount &&
			payment.getMid().equals(mid)
		);
		assertTrue(contains);
	}

	@Then("the payment is not successful")
	public void thePaymentIsNotSuccessful() {
		assertFalse(successful);
	}

	@And("an error message is returned saying {string}")
	public void anErrorMessageIsReturnedSaying(String arg0) {
		assertEquals(errorMsg, arg0);
	}
}
