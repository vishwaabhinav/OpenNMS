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

package org.opennms.netmgt.snmpinterfacepoller.pollable;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.opennms.core.test.OpenNMSJUnit4ClassRunner;
import org.opennms.netmgt.dao.NodeDao;
import org.opennms.netmgt.dao.db.JUnitConfigurationEnvironment;
import org.opennms.netmgt.dao.db.JUnitTemporaryDatabase;
import org.opennms.netmgt.model.NetworkBuilder;
import org.opennms.netmgt.model.OnmsIpInterface;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.OnmsSnmpInterface;
import org.opennms.test.mock.MockLogAppender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(OpenNMSJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
        "classpath:/META-INF/opennms/applicationContext-dao.xml",
        "classpath:/META-INF/opennms/applicationContext-commonConfigs.xml",
        "classpath:/META-INF/opennms/applicationContext-daemon.xml",
        "classpath:/META-INF/opennms/applicationContext-proxy-snmp.xml",
        "classpath:/META-INF/opennms/mockEventIpcManager.xml",
        "classpath:/META-INF/opennms/applicationContext-databasePopulator.xml",
        "classpath:/META-INF/opennms/applicationContext-setupIpLike-enabled.xml",
        "classpath:/META-INF/opennms/applicationContext-snmpinterfacepollerd.xml",
        "classpath:/META-INF/opennms/applicationContext-minimal-conf.xml"
})
@JUnitConfigurationEnvironment
@JUnitTemporaryDatabase
public class PollContextTest {

    @Autowired
    private PollContext m_pollContext;

    @Autowired
    private NodeDao m_nodeDao;

    @Before
    public void setUp() throws Exception {
        MockLogAppender.setupLogging();

        NetworkBuilder nb = new NetworkBuilder();

        nb.addNode("cisco2691").setForeignSource("linkd").setForeignId("cisco2691").setSysObjectId(".1.3.6.1.4.1.9.1.122").setType("A");
        nb.addInterface("10.1.4.2").setIsSnmpPrimary("P").setIsManaged("M")
        .addSnmpInterface(4).setIfType(6).setCollectionEnabled(false).setIfSpeed(10000000).setPhysAddr("c00397a70001").setIfOperStatus(2).setIfDescr("Null0");
        nb.addInterface("10.1.5.1").setIsSnmpPrimary("S").setIsManaged("M")
        .addSnmpInterface(2).setIfType(6).setCollectionEnabled(false).setIfSpeed(100000000).setPhysAddr("c00397a70000").setIfOperStatus(1).setIfDescr("FastEthernet0");
        nb.addInterface("10.1.7.1").setIsSnmpPrimary("S").setIsManaged("M")
        .addSnmpInterface(1).setIfType(6).setCollectionEnabled(false).setIfSpeed(100000000).setPhysAddr("c00397a70010").setIfOperStatus(1).setIfDescr("Ethernet0");
        for (OnmsSnmpInterface iface : nb.getCurrentNode().getSnmpInterfaces()) {
            iface.setPoll("P");
        }
        m_nodeDao.save(nb.getCurrentNode());

        nb.addNode("cisco1700").setForeignSource("linkd").setForeignId("cisco1700").setSysObjectId(".1.3.6.1.4.1.9.1.200").setType("A");
        nb.addInterface("10.1.5.2").setIsSnmpPrimary("P").setIsManaged("M")
        .addSnmpInterface(2).setIfType(6).setCollectionEnabled(true).setIfSpeed(100000000).setPhysAddr("d00297a60000");
        for (OnmsSnmpInterface iface : nb.getCurrentNode().getSnmpInterfaces()) {
            iface.setPoll("P");
        }
        m_nodeDao.save(nb.getCurrentNode());

        m_nodeDao.flush();
    }

    @After
    public void tearDown() throws Exception {
        for (final OnmsNode node : m_nodeDao.findAll()) {
            m_nodeDao.delete(node);
        }
        m_nodeDao.flush();
        MockLogAppender.assertNoWarningsOrGreater();
    }

    /*
     * This test has been designed to verify the workaround for using the IP address on the SNMP
     * Interface based Criteria, because in 1.8, the ipaddr was a valid column of the snmpinterface
     * table, and that column has been removed in order to promote the usage of the ipinterface table.
     */
    @Test
    public void testCriterias() throws Exception {
        Assert.assertNotNull(m_pollContext);

        OnmsNode node = m_nodeDao.findByForeignId("linkd", "cisco2691");
        Assert.assertNotNull(node);

        List<OnmsIpInterface> ipInterfaces = m_pollContext.getPollableNodes();
        Assert.assertNotNull(ipInterfaces);
        Assert.assertEquals(2, ipInterfaces.size());

        ipInterfaces = m_pollContext.getPollableNodesByIp("10.1.4.2"); // Primary Interface
        Assert.assertNotNull(ipInterfaces);
        Assert.assertEquals(1, ipInterfaces.size());

        ipInterfaces = m_pollContext.getPollableNodesByIp("10.1.5.1"); // Secondary Interface
        Assert.assertNotNull(ipInterfaces);
        Assert.assertEquals(0, ipInterfaces.size());

        // Because the criteria is an SQL restriction, the access to ipinterface table is through the
        // internal alias created by hibernate, in this case ipinterfac1_.
        String criteria = "snmpifdescr like '%Ethernet%' and ipinterfac1_.ipaddr like '10.1.5.%'";
        List<OnmsSnmpInterface> snmpInterfaces = m_pollContext.get(node.getId(), criteria);
        Assert.assertNotNull(snmpInterfaces);
        Assert.assertEquals(1, snmpInterfaces.size());
    }

}
