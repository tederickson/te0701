package com.tools.rental.client;

import com.tools.rental.domain.ChangePasswordRequest;
import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.domain.ToolCodeDigest;
import com.tools.rental.enumeration.ToolType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class RentalClient {
    private final RestClient restClient;

    public RentalClient(String server, int port) {
        restClient = RestClient.builder()
                .baseUrl("http://" + server + ":" + port)
                .build();
    }

    public List<ToolCodeDigest> getToolCodes() {
        return restClient.get()
                .uri("/v1/reference/tool-codes")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public CustomerDigest createCustomer(final CreateCustomerRequest request) {
        return restClient.post()
                .uri("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(CustomerDigest.class);
    }

    public CustomerDigest getCustomerByPhone(final String phone) {
        return restClient.get()
                .uri("/v1/customers/{phone}", phone)
                .retrieve()
                .body(CustomerDigest.class);
    }

    public void deleteCustomerByPhone(final String phone) {
        restClient.delete()
                .uri("/v1/customers/{phone}", phone)
                .retrieve()
                .toBodilessEntity();
    }

    public void changePassword(final long id, final ChangePasswordRequest changePasswordRequest) {
        restClient.post()
                .uri("/v1/customers/{id}/change-password", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(changePasswordRequest)
                .retrieve()
                .toBodilessEntity();
    }

    public StoreToolTypeChargeDigest findByStoreIdAndToolType(final short storeId, final ToolType toolType) {
        return restClient.get()
                .uri("/v1/inventory/stores/{storeId}/{toolType}", storeId, toolType)
                .retrieve()
                .body(StoreToolTypeChargeDigest.class);
    }

    public StoreToolTypeChargeDigest createStoreToolTypeCharge(final CreateStoreToolTypeChargeRequest request) {
        return restClient.post()
                .uri("/v1/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(StoreToolTypeChargeDigest.class);
    }

    public void deleteStoreToolTypeCharge(final short storeId, final ToolType toolType) {
        restClient.delete()
                .uri("/v1/inventory/stores/{storeId}/{toolType}", storeId, toolType)
                .retrieve()
                .toBodilessEntity();
    }

    public RentalAgreementDigest toolRentalCheckout(final CheckoutRequest request) {
        return restClient.post()
                .uri("/v1/inventory/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(RentalAgreementDigest.class);
    }

    public String toolRentalCheckoutExportToConsole(final RentalAgreementDigest request) {
        return restClient.post()
                .uri("/v1/inventory/checkout:export")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(String.class);
    }
}
