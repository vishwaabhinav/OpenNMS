/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2012 The OpenNMS Group, Inc.
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

package org.opennms.features.gwt.graph.resource.list.client.view;

import org.opennms.features.gwt.tableresources.client.OnmsTableResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SingleSelectionModel;

public class ResourceTable extends CellTable<ResourceListItem> {
    
    private SingleSelectionModel<ResourceListItem> m_selectionModel;
    
    public ResourceTable() {
        super(15, (CellTable.Resources) GWT.create(OnmsTableResources.class));
        initialize();
    }

    private void initialize() {
        TextColumn<ResourceListItem> resourceColumn = new TextColumn<ResourceListItem>() {
            
            @Override
            public String getValue(ResourceListItem listItem) {
                return "" + listItem.getValue();
            }
            
        };
        
        m_selectionModel = new SingleSelectionModel<ResourceListItem>(); 
        setSelectionModel(m_selectionModel);
        
        addColumn(resourceColumn, "Resources");
        
    }
    
    public ResourceListItem getSelectedResourceItem() {
        return m_selectionModel.getSelectedObject();
    }
}
