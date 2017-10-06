package com.wytings.route.api;

import java.util.Map;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public interface IRouteMapLoader {
    Map<String, Class<?>> load();
}
