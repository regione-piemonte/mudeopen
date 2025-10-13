/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import java.util.Optional;
import java.util.Set;
import org.cts.CRSFactory;
import org.cts.IllegalCoordinateException;
import org.cts.crs.CRSException;
import org.cts.crs.CompoundCRS;
import org.cts.crs.CoordinateReferenceSystem;
import org.cts.crs.GeodeticCRS;
import org.cts.datum.GeodeticDatum;
import org.cts.op.CoordinateOperation;
import org.cts.op.CoordinateOperationException;
import org.cts.op.CoordinateOperationFactory;
import org.cts.op.transformation.FrenchGeocentricNTF2RGF;
import org.cts.op.transformation.GridBasedTransformation;
import org.cts.op.transformation.NTv2GridShiftTransformation;
import org.cts.registry.EPSGRegistry;
import org.cts.registry.RegistryManager;
import org.springframework.stereotype.Component;

@Component
public class CoordinateTrasformationHelper {
    public double[] fromUtm32ToWgs84(double[] utm32coords) throws IllegalCoordinateException, CoordinateOperationException {
        double [] result = transform((GeodeticCRS)getCRS("EPSG:32632").get(), (GeodeticCRS)getCRS("EPSG:4326").get(), utm32coords);
        return result;
    }
    public double[] fromEpsg3003ToWgs84(double[] utm32coords) throws IllegalCoordinateException, CoordinateOperationException {
        double [] result = transform((GeodeticCRS)getCRS("EPSG:3003").get(), (GeodeticCRS)getCRS("EPSG:4326").get(), utm32coords);
        return result;
    }

    public Optional<CoordinateReferenceSystem> getCRS(String authority){
        CRSFactory cRSFactory = new CRSFactory();

        RegistryManager registryManager = cRSFactory.getRegistryManager();
        registryManager.addRegistry(new EPSGRegistry());

        CoordinateReferenceSystem crs = null;
        try {
            crs = cRSFactory.getCRS(authority);
        } catch (CRSException e) {
            e.printStackTrace();
        }

        return Optional.of(crs);

    }

    /**
     * Transform a point from a CRS to another CRS
     *
     * @param sourceCRS
     * @param targetCRS
     * @param inputPoint

     * @throws IllegalCoordinateException
     * @throws org.cts.op.CoordinateOperationException
     */
    public double[] transform(GeodeticCRS sourceCRS, GeodeticCRS targetCRS, double[] inputPoint)
            throws IllegalCoordinateException, CoordinateOperationException {
        Set<CoordinateOperation> ops;
        int tot, subtot;
        ops = CoordinateOperationFactory.createCoordinateOperations(sourceCRS, targetCRS);
        tot = ops.size();
        if (sourceCRS.getDatum() == GeodeticDatum.WGS84 || targetCRS.getDatum() == GeodeticDatum.WGS84) {
            ops = CoordinateOperationFactory.excludeFilter(ops, FrenchGeocentricNTF2RGF.class);
            ops = CoordinateOperationFactory.excludeFilter(ops, NTv2GridShiftTransformation.class);
        }
        // If source CRS comes from the EPSG registry and is not a CompoundCRS,
        // we use BursaWolf or translation rather than GridBasedTransformation,
        // even if a GridBasef Transformation is available (precise transformation
        // may be available because we also read IGNF registry and precise
        // transformations have been stored in GeodeticDatum objects.
        else if (sourceCRS.getIdentifier().getAuthorityName().equals("EPSG") &&
                !(sourceCRS instanceof CompoundCRS) && !(targetCRS instanceof CompoundCRS)) {
            ops = CoordinateOperationFactory.excludeFilter(ops, GridBasedTransformation.class);
        }
        subtot = ops.size();
        if (!ops.isEmpty()) {
            CoordinateOperation op = CoordinateOperationFactory.getMostPrecise(ops);
            double[] input = new double[inputPoint.length];
            System.arraycopy(inputPoint, 0, input, 0, inputPoint.length);
            return op.transform(input);
        } else {

            return new double[]{0.0d, 0.0d, 0.0d};
        }
    }
}
