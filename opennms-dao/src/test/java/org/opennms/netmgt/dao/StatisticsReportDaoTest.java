/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2007-2012 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennms.core.test.OpenNMSJUnit4ClassRunner;
import org.opennms.netmgt.dao.db.JUnitConfigurationEnvironment;
import org.opennms.netmgt.dao.db.JUnitTemporaryDatabase;
import org.opennms.netmgt.model.ResourceReference;
import org.opennms.netmgt.model.StatisticsReport;
import org.opennms.netmgt.model.StatisticsReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * Unit tests for StatisticsReportDao
 * 
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 * @see StatisticsReportDao
 */
@RunWith(OpenNMSJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:/META-INF/opennms/applicationContext-dao.xml",
        "classpath:/META-INF/opennms/applicationContext-databasePopulator.xml",
        "classpath:/META-INF/opennms/applicationContext-setupIpLike-enabled.xml",
        "classpath*:/META-INF/opennms/component-dao.xml"
})
@JUnitConfigurationEnvironment
@JUnitTemporaryDatabase
public class StatisticsReportDaoTest {
	@Autowired
    private StatisticsReportDao m_statisticsReportDao;
	
	@Autowired
    private ResourceReferenceDao m_resourceReferenceDao;

	@Test
	@Transactional
    public void testSave() throws Exception {
        StatisticsReport report = new StatisticsReport();
        report.setName("A Mighty Fine Report");
        report.setDescription("hello!");
        report.setStartDate(new Date());
        report.setEndDate(new Date());
        report.setJobStartedDate(new Date());
        report.setJobCompletedDate(new Date());
        report.setPurgeDate(new Date());
        
		{
            ResourceReference resource = new ResourceReference();
            resource.setResourceId("foo");
            m_resourceReferenceDao.save(resource);

            StatisticsReportData data = new StatisticsReportData();
            data.setReport(report);
            data.setResource(resource);
            data.setValue(0.0);
            report.addData(data);
        }
        

        {
            ResourceReference resource = new ResourceReference();
            resource.setResourceId("bar");
            m_resourceReferenceDao.save(resource);
            

            StatisticsReportData data = new StatisticsReportData();
            data.setReport(report);
            data.setResource(resource);
            data.setValue(0.0);
            report.addData(data);
        }
        
        m_statisticsReportDao.save(report);
    }
}
