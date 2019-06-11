/*
 * Copyright (C) 2015 by Array Systems Computing Inc. http://www.array.ca
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */
package org.csa.rstb.io.radarsat2;

import org.esa.s1tbx.commons.test.ReaderTest;
import org.esa.s1tbx.commons.test.S1TBXTests;
import org.esa.s1tbx.commons.test.TestData;
import org.esa.snap.engine_utilities.gpf.TestProcessor;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * Test Product Reader.
 *
 * @author lveci
 */
public class TestRadarsat2ProductReader extends ReaderTest {

    private static final File folderSLC = new File(S1TBXTests.input,"SAR\\RS2\\RS2_OK76385_PK678063_DK606752_FQ2_20080415_143807_HH_VV_HV_VH_SLC");
    private static final File metadataSLC = new File(S1TBXTests.input, "SAR\\RS2\\RS2_OK76385_PK678063_DK606752_FQ2_20080415_143807_HH_VV_HV_VH_SLC\\product.xml");

    public TestRadarsat2ProductReader() {
        super(new Radarsat2ProductReaderPlugIn());
    }

    @Test
    public void testOpenAll() throws Exception {
        TestProcessor testProcessor = S1TBXTests.createS1TBXTestProcessor();
        testProcessor.recurseReadFolder(this, S1TBXTests.rootPathsRadarsat2, readerPlugIn, reader, null, null);
    }

    @Test
    @Ignore
    public void testOpeningFolder() throws Exception {
        testReader(folderSLC);
    }

    @Test
    public void testOpeningMetadataFile() throws Exception {
        testReader(metadataSLC);
    }

    @Test
    public void testOpeningZip() throws Exception {
        testReader(TestData.inputRS2_SQuad);
    }
}
