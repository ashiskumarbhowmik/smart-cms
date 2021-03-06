/*
 *
 * This is a simple Content Management System (CMS)
 * Copyright (C) 2010  Imran M Yousuf (imyousuf@smartitengineering.com)
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
package com.smartitengineering.cms.ws.common;

import com.smartitengineering.cms.ws.common.domains.CollectionFieldValueImpl;
import com.smartitengineering.cms.ws.common.domains.Content;
import com.smartitengineering.cms.ws.common.domains.ContentImpl;
import com.smartitengineering.cms.ws.common.domains.FieldImpl;
import com.smartitengineering.cms.ws.common.domains.FieldValue;
import com.smartitengineering.cms.ws.common.domains.FieldValueImpl;
import com.smartitengineering.cms.ws.common.domains.OtherFieldValueImpl;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class ContentDeSerializationTest extends TestCase {

  private static final String CONTENT = "{\"fields\":[{\"name\":\"field1\",\"value\":{\"@valueType\":\"integer\"," +
      "\"value\":\"1\",\"type\":\"integer\"},\"fieldUri\":\"field1Uri\"},{\"name\":\"field2\",\"value\":{" +
      "\"@valueType\":\"other\",\"mimeType\":\"text/plain\",\"value\":\"1\",\"type\":\"other\"}," +
      "\"fieldUri\":\"field1Uri\"},{\"name\":\"field3\",\"value\":{\"@valueType\":\"string\",\"mimeType\":\"text/xml\"," +
      "\"value\":\"1\",\"type\":\"string\"},\"fieldUri\":\"field1Uri\"},{\"name\":\"field4\",\"value\":{" +
      "\"@valueType\":\"collection\",\"values\":[{\"@valueType\":\"string\",\"mimeType\":\"text/xml\",\"value\":\"1\"," +
      "\"type\":\"string\"},{\"@valueType\":\"string\",\"mimeType\":\"text/xml\",\"value\":\"2\",\"type\":\"string\"}," +
      "{\"@valueType\":\"string\",\"mimeType\":\"text/xml\",\"value\":\"3\",\"type\":\"string\"}]," +
      "\"type\":\"collection\"},\"fieldUri\":\"field1Uri\"}],\"creationDate\":1286889793183," +
      "\"lastModifiedDate\":1286889793183,\"contentTypeUri\":\"someUri\",\"parentContentUri\":\"parentUri\"," +
      "\"status\":\"someStatus\"}";
  private Content content;
  private ObjectMapper mapper;

  @Override
  protected void setUp() throws Exception {
    mapper = new ObjectMapper();
    ContentImpl contentImpl = new ContentImpl();
    contentImpl.setContentTypeUri("someUri");
    contentImpl.setCreationDate(new Date(1286889793183l));
    contentImpl.setLastModifiedDate(new Date(1286889793183l));
    contentImpl.setParentContentUri("parentUri");
    contentImpl.setStatus("someStatus");
    FieldImpl field = new FieldImpl();
    contentImpl.getFields().add(field);
    field.setFieldUri("field1Uri");
    field.setName("field1");
    FieldValueImpl simpleValue = new FieldValueImpl();
    simpleValue.setType("integer");
    simpleValue.setValue("1");
    field.setValue(simpleValue);

    field = new FieldImpl();
    contentImpl.getFields().add(field);
    field.setFieldUri("field1Uri");
    field.setName("field2");
    OtherFieldValueImpl otherValue = new OtherFieldValueImpl();
    otherValue.setType("other");
    otherValue.setValue("1");
    otherValue.setMimeType("text/plain");
    field.setValue(otherValue);

    field = new FieldImpl();
    contentImpl.getFields().add(field);
    field.setFieldUri("field1Uri");
    field.setName("field3");
    OtherFieldValueImpl stringValue = new OtherFieldValueImpl();
    stringValue.setType("string");
    stringValue.setValue("1");
    stringValue.setMimeType("text/xml");
    field.setValue(stringValue);

    field = new FieldImpl();
    contentImpl.getFields().add(field);
    field.setFieldUri("field1Uri");
    field.setName("field4");
    CollectionFieldValueImpl collectionValue = new CollectionFieldValueImpl();
    collectionValue.setType("collection");
    {
      OtherFieldValueImpl stringValue2 = new OtherFieldValueImpl();
      stringValue2.setType("string");
      stringValue2.setValue("1");
      stringValue2.setMimeType("text/xml");
      OtherFieldValueImpl stringValue3 = new OtherFieldValueImpl();
      stringValue3.setType("string");
      stringValue3.setValue("2");
      stringValue3.setMimeType("text/xml");
      OtherFieldValueImpl stringValue4 = new OtherFieldValueImpl();
      stringValue4.setType("string");
      stringValue4.setValue("3");
      stringValue4.setMimeType("text/xml");
      collectionValue.setValues(Arrays.<FieldValue>asList(stringValue2, stringValue3, stringValue4));
    }
    field.setValue(collectionValue);
    content = contentImpl;
  }

  public void testSerialization() throws Exception {
    StringWriter writer = new StringWriter();
    mapper.writeValue(writer, content);
    assertEquals(CONTENT, writer.toString());
  }

  public void testDeserializationWithReserialization() throws Exception {
    StringWriter writer = new StringWriter();
    mapper.writeValue(writer, mapper.readValue(IOUtils.toInputStream(CONTENT), ContentImpl.class));
    assertEquals(CONTENT, writer.toString());
  }
}
