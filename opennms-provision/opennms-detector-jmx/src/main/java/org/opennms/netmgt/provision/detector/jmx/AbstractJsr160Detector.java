/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2009-2012 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.provision.detector.jmx;

import org.opennms.netmgt.provision.detector.jmx.client.JMXClient;
import org.opennms.netmgt.provision.detector.jmx.client.Jsr160Client;

/**
 * <p>Abstract AbstractJsr160Detector class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public abstract class AbstractJsr160Detector extends JMXDetector {
   
    /** Constant <code>DEFAULT_PORT=9003</code> */
    protected static int DEFAULT_PORT = 9003;
    
    private String m_factory = "STANDARD";
    private String m_friendlyName = "jsr160";
    private String m_protocol = "rmi";
    private String m_type = "default";
    private String m_urlPath = "/jmxrmi";
    private String m_username = "opennms";
    private String m_password = "OPENNMS";
    
    /**
     * <p>Constructor for AbstractJsr160Detector.</p>
     *
     * @param serviceName a {@link java.lang.String} object.
     * @param port a int.
     */
    protected AbstractJsr160Detector(String serviceName, int port) {
        super(serviceName, port);
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    protected JMXClient getClient() {
        Jsr160Client client = new Jsr160Client();
        client.setFactory(getFactory());
        client.setFriendlyName(getFriendlyName());
        client.setProtocol(getProtocol());
        client.setUrlPath(getUrlPath());
        client.setUsername(getUsername());
        client.setPassword(getPassword());
        client.setType(getType());
        
        return client;
    }

    /**
     * <p>setFactory</p>
     *
     * @param factory a {@link java.lang.String} object.
     */
    public void setFactory(String factory) {
        m_factory = factory;
    }

    /**
     * <p>getFactory</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFactory() {
        return m_factory;
    }

    /**
     * <p>setFriendlyName</p>
     *
     * @param friendlyName a {@link java.lang.String} object.
     */
    public void setFriendlyName(String friendlyName) {
        m_friendlyName = friendlyName;
    }

    /**
     * <p>getFriendlyName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFriendlyName() {
        return m_friendlyName;
    }

    /**
     * <p>setProtocol</p>
     *
     * @param protocol a {@link java.lang.String} object.
     */
    public void setProtocol(String protocol) {
        m_protocol = protocol;
    }

    /**
     * <p>getProtocol</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getProtocol() {
        return m_protocol;
    }

    /**
     * <p>setType</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    public void setType(String type) {
        m_type = type;
    }

    /**
     * <p>getType</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getType() {
        return m_type;
    }

    /**
     * <p>setUrlPath</p>
     *
     * @param urlPath a {@link java.lang.String} object.
     */
    public void setUrlPath(String urlPath) {
        m_urlPath = urlPath;
    }

    /**
     * <p>getUrlPath</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUrlPath() {
        return m_urlPath;
    }

    /**
     * <p>setUsername</p>
     *
     * @param username a {@link java.lang.String} object.
     */
    public void setUsername(String username) {
        m_username = username;
    }

    /**
     * <p>getUsername</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUsername() {
        return m_username;
    }

    /**
     * <p>setPassword</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        m_password = password;
    }

    /**
     * <p>getPassword</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return m_password;
    }
}
