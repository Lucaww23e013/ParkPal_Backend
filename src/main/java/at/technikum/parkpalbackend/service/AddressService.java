package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(String addressId, Address updatedAddress) {
        Address address = addressRepository.findAddressByAddressId(addressId).orElseThrow();
        address.setAddressId(updatedAddress.getAddressId());
        address.setStreetNumber(updatedAddress.getStreetNumber());
        address.setZipCode(updatedAddress.getZipCode());
        address.setCity(updatedAddress.getCity());
        address.setCountry(updatedAddress.getCountry());

        return addressRepository.save(address);
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Address findAddressByAddressId(String addressId) {
        return addressRepository.findAddressByAddressId(addressId).orElseThrow();
    }

    public Address findAddressByCountry(Country country) {
        return addressRepository.findAddressByCountry(country).orElseThrow();
    }

    public Address findAddressByAddressByCity(String city) {
        return addressRepository.findAddressByCity(city).orElseThrow();
    }

    public Address deleteAddressByAddressId(String addressId) {
        Address deletedAddress = addressRepository.findAddressByAddressId(addressId).orElseThrow();
        addressRepository.delete(deletedAddress);
        return null;
    }
}
