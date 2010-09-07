/*
 *
 * This is a simple Content Management System (CMS)
 * Copyright (C) 2009  Imran M Yousuf (imyousuf@smartitengineering.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.smartitengineering.cms.api.content;

import com.smartitengineering.cms.api.type.ContentStatus;
import com.smartitengineering.cms.api.type.ContentTypeId;
import com.smartitengineering.dao.common.queryparam.QueryParameter;
import java.util.Date;

/**
 *
 * @author imyousuf
 */
public interface Filter {

  void addContentTypeToFilter(ContentTypeId... types);

  void removeContentTypeFromFilter(ContentTypeId... types);

  void setCreationDateFilter(QueryParameter<Date> creationDateParam);

  void setLastModifiedDateFilter(QueryParameter<Date> creationDateParam);

  void addFieldFilter(QueryParameter... parameters);

  void removeFieldFilter(QueryParameter... parameters);

  void addStatusFilter(ContentStatus... status);

  void removeStatusFilter(ContentStatus... status);
}