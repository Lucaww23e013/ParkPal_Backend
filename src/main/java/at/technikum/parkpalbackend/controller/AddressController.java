package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.AddressDto;
import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.mapper.AddressMapper;
import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@CrossOrigin
public class AddressController {
    public final AddressService addressService;

    public final AddressMapper addressMapper;


    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @PostMapping("/addresses")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto addAddresses(@RequestBody @Valid AddressDto addressDto){
        Address address = addressMapper.toEntity(addressDto);
        address = addressService.save(address);
        return addressMapper.toDto(address);
    }

    @PutMapping("/addresses/{addressId}")
    public AddressDto updateAddress(@PathVariable String addressId, @RequestBody AddressDto updatedAddressDto){
        Address updatedAddress = addressMapper.toEntity(updatedAddressDto);
        updatedAddress = addressService.updateAddress(addressId, updatedAddress);
        return addressMapper.toDto(updatedAddress);
    }

    @GetMapping("/addresses")
    public List<AddressDto> getAllAddresses() {
        List<Address> allAddresses = addressService.findAllAddresses();
        return allAddresses.stream().map(address -> addressMapper.toDto(address)).toList();
    }
    @GetMapping("/addresses/{addressId}")
    public AddressDto getCountryById(@PathVariable @Valid String addressId){
        Address address = addressService.findAddressByAddressId(addressId);
        return addressMapper.toDto(address);
    }

    @GetMapping("/addresses/{country}")
    public AddressDto getCountryByCountry(@PathVariable @Valid Country country){
        Address address = addressService.findAddressByCountry(country);
        return addressMapper.toDto(address);
    }

    @GetMapping("/addresses/{city}")
    public AddressDto getCountryByCity(@PathVariable @Valid String city){
        Address address = addressService.findAddressByAddressByCity(city);
        return addressMapper.toDto(address);
    }
    @DeleteMapping("/addresses/{addressId}")
    //@Preauthorize with Spring security later
    public AddressDto deleteAddressByAddressId(@PathVariable @Valid String addressId){
        Address address = addressService.deleteAddressByAddressId(addressId);
        return null;
    }
}
