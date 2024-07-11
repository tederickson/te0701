package com.tools.rental.domain;

import com.google.code.beanmatchers.BeanMatchers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class StoreToolTypeChargeDigestTest {
    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(StoreToolTypeChargeDigest.class, hasValidBeanConstructor());
    }

    @Test
    void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(StoreToolTypeChargeDigest.class, hasValidGettersAndSetters());
    }

    @Test
    void allPropertiesShouldBeRepresentedInToStringOutput() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        assertThat(StoreToolTypeChargeDigest.class, hasValidBeanToString());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(StoreToolTypeChargeDigest.class).suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.BIGDECIMAL_EQUALITY)
                .verify();
    }

    @Test
    void builderToString() {
        assertThat(StoreToolTypeChargeDigest.builder().toString(), is(notNullValue()));
    }
}