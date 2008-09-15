/*
 * $Id: $
 *
 * Copyright (C) 2008 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.esa.beam.dataio.smos;


import com.bc.ceres.binio.*;
import com.bc.ceres.binio.util.RandomAccessFileIOHandler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Created by marcoz.
 *
 * @author marcoz
 * @version $Revision: $ $Date: $
 */
public class SmosFile {

    private final SequenceData gridPointList;
    private int[] gridPointIndexes;

    public SmosFile(File file, Format format) throws IOException {
        System.out.println("SmosFile: file = " + file);
        System.out.println("SmosFile: format = " + format.getName());
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        final IOHandler handler = new RandomAccessFileIOHandler(raf);
        final IOContext context = new IOContext(format, handler);
        CompoundData smosBinaryData = context.getData();
        gridPointList = smosBinaryData.getSequence("Grid_Point_List");
        initGridPointIndexes();
    }

    public float getMeanBtData(int gridPointIndex, int btDataIndex) throws IOException {
        CompoundData gridPointEntry = gridPointList.getCompound(gridPointIndex);
        SequenceData btDataList = gridPointEntry.getSequence(6);
        final int btDataListCount = btDataList.getElementCount();
        float mean = 0.0f;
        int n = Math.min(1, btDataListCount);
        for (int i = 0; i < n; i++) {
            CompoundData btData = btDataList.getCompound(i);
            mean += btData.getFloat(btDataIndex);
        }
        return mean / n;
    }

    public int getGridPointId(int seqNum) {
        return gridPointIndexes[seqNum];
    }

    public void initGridPointIndexes() throws IOException {
        // todo - OPT: first find seqnumMin, seqnumMax, then gridPointIndexes=new int[seqnumMax-seqnumMin+1];
        gridPointIndexes = new int[2621442 + 1];
        Arrays.fill(gridPointIndexes, -1);
        final int gridPointCounter = gridPointList.getElementCount();
        for (int i = 0; i < gridPointCounter; i++) {
            CompoundData gridPointData = gridPointList.getCompound(i);
            final int gridPointId = gridPointData.getInt(0);
            final int seqnum = SmosDgg.smosGridPointIdToDggridSeqnum(gridPointId);
            gridPointIndexes[seqnum] = i;
        }
        System.out.println("SmosFile: gridPointCounter = " + gridPointCounter);
        System.out.println("SmosFile: gridPointIndexes.length = " + gridPointIndexes.length);
        int n = 0;
        for (int gridPointIndex : gridPointIndexes) {
            if (gridPointIndex != -1) {
                n++;
            }
        }
        System.out.println("SmosFile: number of gridPointIndexes != -1: " + n);
    }
}
