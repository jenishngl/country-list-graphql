package org.jjhome;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.jjhome.country.Country;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@GraphQLApi
public class CountryListResource {

    private Set<Country> countries = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public CountryListResource() {
        countries.add(new Country("IN", "India"));
        countries.add(new Country("SG", "Singapore"));
    }

    @Query
    @Description("List Countries")
    @Produces(MediaType.APPLICATION_JSON)
    public String country(@DefaultValue("") String code) {
        if (code.isEmpty()) {
            return countries.stream().map(Country::toString).collect(Collectors.joining(", "));
        } else {
            return countries.stream().filter(c -> c.getCode().equals(code)).findFirst().map(Country::getName).orElse("");
        }
    }

    @Mutation("addCountry")
    @Description("Add Country")
    @Produces(MediaType.APPLICATION_JSON)
    public Country addCountry(String code, String name) {
        Country country = new Country(code, name);
        countries.add(country);
        return country;
    }
}