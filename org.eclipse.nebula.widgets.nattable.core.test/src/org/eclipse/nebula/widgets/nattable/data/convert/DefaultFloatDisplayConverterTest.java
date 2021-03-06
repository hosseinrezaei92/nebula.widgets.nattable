/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.data.convert;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultFloatDisplayConverterTest {

    private DefaultFloatDisplayConverter floatConverter = new DefaultFloatDisplayConverter();

    private static Locale defaultLocale;

    @BeforeClass
    public static void setup() {
        defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en"));
    }

    @AfterClass
    public static void tearDown() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testNonNullDataToDisplay() {
        Assert.assertEquals("123.0",
                this.floatConverter.canonicalToDisplayValue(Float.valueOf("123")));
        Assert.assertEquals("23.5",
                this.floatConverter.canonicalToDisplayValue(Float.valueOf("23.5")));
    }

    @Test
    public void testNullDataToDisplay() {
        Assert.assertNull(this.floatConverter.canonicalToDisplayValue(null));
    }

    @Test
    public void testNonNullDisplayToData() {
        Assert.assertEquals(Float.valueOf("123"),
                this.floatConverter.displayToCanonicalValue("123"));
        Assert.assertEquals(Float.valueOf("23.5"),
                this.floatConverter.displayToCanonicalValue("23.5"));
    }

    @Test
    public void testNullDisplayToData() {
        Assert.assertNull(this.floatConverter.displayToCanonicalValue(""));
    }

    @Test(expected = ConversionFailedException.class)
    public void testConversionException() {
        this.floatConverter.displayToCanonicalValue("abc");
    }

    @Test
    public void testLocalizedDisplayConversion() {
        NumberFormat original = this.floatConverter.getNumberFormat();
        NumberFormat localized = NumberFormat.getInstance(Locale.GERMAN);
        localized.setMinimumFractionDigits(1);
        localized.setMaximumFractionDigits(2);

        this.floatConverter.setNumberFormat(localized);
        Assert.assertEquals("123,0",
                this.floatConverter.canonicalToDisplayValue(Float.valueOf("123")));

        this.floatConverter.setNumberFormat(original);
    }

    @Test
    public void testLocalizedCanonicalConversion() {
        NumberFormat original = this.floatConverter.getNumberFormat();
        NumberFormat localized = NumberFormat.getInstance(Locale.GERMAN);
        localized.setMinimumFractionDigits(1);
        localized.setMaximumFractionDigits(2);

        this.floatConverter.setNumberFormat(localized);
        Object result = this.floatConverter.displayToCanonicalValue("123,5");
        Assert.assertTrue(result instanceof Float);
        Assert.assertEquals(123.5f, result);

        this.floatConverter.setNumberFormat(original);
    }
}
