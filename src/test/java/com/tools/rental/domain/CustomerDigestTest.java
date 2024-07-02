package com.tools.rental.domain;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersExcluding;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class CustomerDigestTest {

    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(CustomerDigest.class, hasValidBeanConstructor());
    }

    @Test
    void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(CustomerDigest.class, hasValidGettersAndSettersExcluding("createDate"));
    }

    @Test
    void allPropertiesShouldBeRepresentedInToStringOutput() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        assertThat(CustomerDigest.class, hasValidBeanToString());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(CustomerDigest.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    void builderToString() {
        assertThat(CustomerDigest.builder().toString(), is(notNullValue()));
    }
}