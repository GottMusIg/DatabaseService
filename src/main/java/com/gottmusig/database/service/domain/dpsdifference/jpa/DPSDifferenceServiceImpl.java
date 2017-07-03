package com.gottmusig.database.service.domain.dpsdifference.jpa;

import com.gottmusig.database.service.domain.dpsdifference.DPSDifferenceService;
import com.gottmusig.database.service.domain.dpsdifference.SpecificationDPS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class DPSDifferenceServiceImpl implements DPSDifferenceService {

    private static final Sort DPSDIFFERENCE_SORT_ORDER = new Sort(Sort.Direction.DESC, "dps");

    @Autowired SpecificationDPSEntity.SpecificationDPSRepository dpsRepository;

    @Override
    public List<SpecificationDPS> getDPSDifference() {
        return dpsRepository.findByDpsGreaterThan(0, DPSDIFFERENCE_SORT_ORDER);
    }

    @Override
    public int getMaxDPSValue() {
        return dpsRepository.findFirstByDpsGreaterThan(0, DPSDIFFERENCE_SORT_ORDER).getDPS();
    }
}
