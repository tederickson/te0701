package com.tools.rental.domain;

import com.google.code.beanmatchers.BeanMatchers;
import com.tools.rental.enumeration.ToolCode;
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

class RentalAgreementDigestTest {
    @Test
    void shouldHaveNoArgsConstructor() {
        assertThat(RentalAgreementDigest.class, hasValidBeanConstructor());
    }

    @Test
    void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(RentalAgreementDigest.class, hasValidGettersAndSettersExcluding("checkoutDate",
                                                                                   "dueDate"));
    }

    @Test
    void allPropertiesShouldBeRepresentedInToStringOutput() {
        BeanMatchers.registerValueGenerator(LocalDate::now, LocalDate.class);
        assertThat(RentalAgreementDigest.class, hasValidBeanToString());
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(RentalAgreementDigest.class).suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.BIGDECIMAL_EQUALITY)
                .verify();
    }

    @Test
    void builderToString() {
        assertThat(RentalAgreementDigest.builder().toString(), is(notNullValue()));
    }

    @Test
    void allArgs() {
        final ToolCode toolCode = ToolCode.JAKD;
        final var rentalAgreement = RentalAgreementDigest.builder()
                .withToolCode(toolCode)
                .withToolType(toolCode.getToolType())
                .withToolBrand(toolCode.getBrand())
                .withRentalDayCount(4)
                .build();
        assertThat(rentalAgreement.getRentalDayCount(), is(4));
    }
}