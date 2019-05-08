package com.centurylink.mdw.service.data.process;

import com.centurylink.mdw.cache.CacheService;
import com.centurylink.mdw.cache.CachingException;
import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.model.workflow.LinkedProcess;
import com.centurylink.mdw.services.ServiceLocator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HierarchyCache implements CacheService {

    private static volatile Map<Long,List<LinkedProcess>> hierarchies = new ConcurrentHashMap<>();

    public static List<LinkedProcess> getHierarchy(Long processId) {
        List<LinkedProcess> hierarchy;
        Map<Long,List<LinkedProcess>> hierarchyMap = hierarchies;
        if (hierarchyMap.containsKey(processId)){
            hierarchy = hierarchyMap.get(processId);
        } else {
            synchronized(HierarchyCache.class) {
                hierarchyMap = hierarchies;
                if (hierarchyMap.containsKey(processId))
                    hierarchy = hierarchyMap.get(processId);
                else {
                    hierarchy = loadHierarchy(processId);
                    if (hierarchy != null) {
                        hierarchies.put(processId, hierarchy);
                    }
                }
            }
        }
        return hierarchy;
    }

    private static List<LinkedProcess> loadHierarchy(Long processId) {
        try {
            return ServiceLocator.getDesignServices().getProcessHierarchy(processId);
        }
        catch (ServiceException ex) {
            throw new CachingException("Failed to load hierarchy for: " + processId, ex);
        }
    }

    @Override
    public void clearCache() {
        hierarchies = new ConcurrentHashMap<>();
    }

    @Override
    public void refreshCache() {
        // hierarchies are lazily loaded
        clearCache();
    }
}
