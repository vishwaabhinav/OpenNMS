/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.poller.remote.gwt.client.events;

import org.opennms.features.poller.remote.gwt.client.ApplicationInfo;

import com.google.gwt.event.shared.GwtEvent;

/**
 * <p>ApplicationDeselectedEvent class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public class ApplicationDeselectedEvent extends GwtEvent<ApplicationDeselectedEventHandler> {
    
    /** Constant <code>TYPE</code> */
    public static Type<ApplicationDeselectedEventHandler> TYPE = new Type<ApplicationDeselectedEventHandler>();
    private ApplicationInfo m_appInfo;
    
    /**
     * <p>Constructor for ApplicationDeselectedEvent.</p>
     *
     * @param appInfo a {@link org.opennms.features.poller.remote.gwt.client.ApplicationInfo} object.
     */
    public ApplicationDeselectedEvent(ApplicationInfo appInfo) {
        setAppInfo(appInfo);
    }
    
    /** {@inheritDoc} */
    @Override
    protected void dispatch(ApplicationDeselectedEventHandler handler) {
        handler.onApplicationDeselected(this);
    }

    /** {@inheritDoc} */
    @Override
    public Type<ApplicationDeselectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    /**
     * <p>setAppInfo</p>
     *
     * @param appInfo a {@link org.opennms.features.poller.remote.gwt.client.ApplicationInfo} object.
     */
    protected void setAppInfo(ApplicationInfo appInfo) {
        m_appInfo = appInfo;
    }

    /**
     * <p>getAppInfo</p>
     *
     * @return a {@link org.opennms.features.poller.remote.gwt.client.ApplicationInfo} object.
     */
    public ApplicationInfo getAppInfo() {
        return m_appInfo;
    }

}