package edu.miu.cs489.repoTest;


import edu.miu.cs489.model.Address;
import edu.miu.cs489.repository.AddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should save and retrieve an address")
    public void testSaveAndRetrieveAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Fairfield");
        address.setState("IA");
        address.setZipCode("52557");
        address.setCountry("USA");

        Address saved = addressRepository.save(address);

        Optional<Address> found = addressRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCity()).isEqualTo("Fairfield");
        assertThat(found.get().getStreet()).isEqualTo("123 Main St");
    }

    @Test
    @DisplayName("Should delete address by ID")
    public void testDeleteAddress() {
        Address address = new Address();
        address.setStreet("456 Elm St");
        address.setCity("Ottumwa");
        address.setState("IA");
        address.setZipCode("52501");
        address.setCountry("USA");

        Address saved = addressRepository.save(address);
        Long id = saved.getId();

        addressRepository.deleteById(id);

        assertThat(addressRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("Should update address fields")
    public void testUpdateAddress() {
        Address address = new Address();
        address.setStreet("789 Oak Ave");
        address.setCity("Des Moines");
        address.setState("IA");
        address.setZipCode("50310");
        address.setCountry("USA");

        Address saved = addressRepository.save(address);
        saved.setCity("Iowa City");
        saved.setZipCode("52240");

        Address updated = addressRepository.save(saved);

        Optional<Address> found = addressRepository.findById(updated.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCity()).isEqualTo("Iowa City");
        assertThat(found.get().getZipCode()).isEqualTo("52240");
    }
}