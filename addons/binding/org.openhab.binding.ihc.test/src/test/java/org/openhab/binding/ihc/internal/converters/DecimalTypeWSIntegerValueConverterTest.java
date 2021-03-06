/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.ihc.internal.converters;

import static org.junit.Assert.assertEquals;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.types.Type;
import org.junit.Test;
import org.openhab.binding.ihc.internal.ws.exeptions.ConversionException;
import org.openhab.binding.ihc.internal.ws.resourcevalues.WSIntegerValue;
import org.openhab.binding.ihc.internal.ws.resourcevalues.WSResourceValue;

/**
 * Test for IHC / ELKO binding
 *
 * @author Pauli Anttila - Initial contribution
 */
public class DecimalTypeWSIntegerValueConverterTest {

    @Test
    public void testConversion() throws ConversionException {
        WSIntegerValue val = new WSIntegerValue(12345, 0, -100, 100);

        val = convertFromOHType(val, new DecimalType(2), new ConverterAdditionalInfo(null, false));
        assertEquals(12345, val.getResourceID());
        assertEquals(2, val.getInteger());

        DecimalType type = convertFromResourceValue(val, new ConverterAdditionalInfo(null, false));
        assertEquals(new DecimalType(2), type);
    }

    @Test(expected = ConversionException.class)
    public void testMinExceed() throws ConversionException {
        WSIntegerValue val = new WSIntegerValue(12345, 0, -100, 100);
        val = convertFromOHType(val, new DecimalType(-101.5), new ConverterAdditionalInfo(null, false));
    }

    @Test(expected = ConversionException.class)
    public void testMaxExceed() throws ConversionException {
        WSIntegerValue val = new WSIntegerValue(12345, 0, -100, 100);
        val = convertFromOHType(val, new DecimalType(101.5), new ConverterAdditionalInfo(null, false));
    }

    private WSIntegerValue convertFromOHType(WSIntegerValue IHCvalue, Type OHval,
            ConverterAdditionalInfo converterAdditionalInfo) throws ConversionException {
        Converter<WSResourceValue, Type> converter = ConverterFactory.getInstance().getConverter(IHCvalue.getClass(),
                DecimalType.class);
        return (WSIntegerValue) converter.convertFromOHType(OHval, IHCvalue, converterAdditionalInfo);
    }

    private DecimalType convertFromResourceValue(WSIntegerValue IHCvalue,
            ConverterAdditionalInfo converterAdditionalInfo) throws ConversionException {
        Converter<WSResourceValue, Type> converter = ConverterFactory.getInstance().getConverter(IHCvalue.getClass(),
                DecimalType.class);
        return (DecimalType) converter.convertFromResourceValue(IHCvalue, converterAdditionalInfo);
    }
}
