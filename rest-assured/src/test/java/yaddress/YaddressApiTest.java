package yaddress;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class YaddressApiTest {

    private String url;
    Map<String, String> params;

    @Before
    public void setup() {
        url = YaddressApi.ADDRESS_API_URL;
        params = new HashMap<String, String>();
    }

    @Test
    public void validUSAddress_shouldReturnNoError() {
        this.params.put("AddressLine1", "506 4TH AVE APT 1");
        this.params.put("AddressLine2", "ASBURY PARK NJ 07712-6086");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(0))
                .body("ErrorMessage", CoreMatchers.equalTo(""))
                .body("Number", CoreMatchers.equalTo("506"))
                .body("Street", CoreMatchers.equalTo("4TH"))
                .body("City", CoreMatchers.equalTo("ASBURY PARK"));
    }

    @Test
    public void emptyAddressesLines_shouldReturnInvalidLineError() {
        this.params.put("AddressLine1", "");
        this.params.put("AddressLine2", "");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(2))
                .body("ErrorMessage", CoreMatchers.equalTo("Invalid address: invalid City-State-Zip line"))
                .body("Number", CoreMatchers.equalTo(""))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo(""));
    }

    @Test
    public void notPassingParameters_shouldReturnInvalidLineError() {
        RestAssured
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(2))
                .body("ErrorMessage", CoreMatchers.equalTo("Invalid address: invalid City-State-Zip line"))
                .body("Number", CoreMatchers.equalTo(""))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo(""));
    }

    @Test
    public void informingSpecialCharacters_shouldReturnInvalidLineError() {
        this.params.put("AddressLine1", "!@#$%*");
        this.params.put("AddressLine2", "!@#$%*");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(2))
                .body("ErrorMessage", CoreMatchers.equalTo("Invalid address: no Zip or State given"))
                .body("Number", CoreMatchers.equalTo(""))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo("!@#$%*"));
    }

    @Test
    public void validBrazilianAddress_shouldReturnStreetNotFoundInZipCodeError() {
        this.params.put("AddressLine1", "Xanxere street 650");
        this.params.put("AddressLine2", "Joinville 89221-550");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(3))
                .body("ErrorMessage", CoreMatchers.equalTo("Street not found in Zip code"))
                .body("Number", CoreMatchers.equalTo("650"))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo("JOINVILLE"));
    }

    @Test
    public void validStreetInWrongCity_shouldReturnStreetNotFoundInCityStateError() {
        this.params.put("AddressLine1", "Mapple street 150");
        this.params.put("AddressLine2", "NY New York");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(3))
                .body("ErrorMessage", CoreMatchers.equalTo("Street not found in city/state"))
                .body("Number", CoreMatchers.equalTo("150"))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo("NEW YORK"));
    }

    @Test
    public void validUSAddressWithInvalidCity_shouldReturnInvalidCityError() {
        this.params.put("AddressLine1", "506 4TH AVE APT 1");
        this.params.put("AddressLine2", "MUNICH DE");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(4))
                .body("ErrorMessage", CoreMatchers.equalTo("City not found in state"))
                .body("Number", CoreMatchers.equalTo("506"))
                .body("Street", CoreMatchers.equalTo(""))
                .body("City", CoreMatchers.equalTo("MUNICH"));
    }

    @Test
    public void validUSAddressWithInvalidHouseNumber_shouldReturnNoSuchNumberError() {
        this.params.put("AddressLine1", "123456 4TH AVE APT 1");
        this.params.put("AddressLine2", "ASBURY PARK NJ 07712-6086");
        RestAssured.with().params(params)
                .get(url)
                .then().statusCode(200)
                .assertThat()
                .body("ErrorCode", CoreMatchers.equalTo(8))
                .body("ErrorMessage", CoreMatchers.equalTo("No such house number in the street"))
                .body("Number", CoreMatchers.equalTo("123456"))
                .body("Street", CoreMatchers.equalTo("4TH"))
                .body("City", CoreMatchers.equalTo("ASBURY PARK"));
    }
}
