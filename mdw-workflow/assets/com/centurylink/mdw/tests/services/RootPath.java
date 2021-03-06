package com.centurylink.mdw.tests.services;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.services.rest.JsonRestService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.Path;
import java.util.Map;

/**
 * TODO: changing below to "/" path breaks SwaggerValidatorActivity
 */
@Path("/todo")
public class RootPath extends JsonRestService {

    @Override
    public JSONObject get(String path, Map<String,String> headers) throws ServiceException, JSONException {
        JSONObject json = new JSONObject();
        json.put("impl", getClass().getName());
        return json;
    }
}
