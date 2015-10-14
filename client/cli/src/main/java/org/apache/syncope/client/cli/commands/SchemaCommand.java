/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.client.cli.commands;

import java.util.Arrays;
import javax.xml.ws.WebServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.syncope.client.cli.Command;
import org.apache.syncope.client.cli.Input;
import org.apache.syncope.client.cli.SyncopeServices;
import org.apache.syncope.common.lib.SyncopeClientException;
import org.apache.syncope.common.lib.to.AbstractSchemaTO;
import org.apache.syncope.common.lib.to.DerSchemaTO;
import org.apache.syncope.common.lib.to.PlainSchemaTO;
import org.apache.syncope.common.lib.to.VirSchemaTO;
import org.apache.syncope.common.lib.types.SchemaType;
import org.apache.syncope.common.rest.api.service.SchemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(name = "schema")
public class SchemaCommand extends AbstractCommand {

    private static final Logger LOG = LoggerFactory.getLogger(SchemaCommand.class);

    private static final String HELP_MESSAGE = "Usage: schema [options]\n"
            + "  Options:\n"
            + "    --help \n"
            + "    --list-all\n"
            + "    --list {SCHEMA-TYPE}\n"
            + "       Schema type: PLAIN / DERIVED / VIRTUAL";

    @Override
    public void execute(final Input input) {
        LOG.debug("Option: {}", input.getOption());
        LOG.debug("Parameters:");
        for (final String parameter : input.getParameters()) {
            LOG.debug("   > " + parameter);
        }

        String[] parameters = input.getParameters();

        if (StringUtils.isBlank(input.getOption())) {
            input.setOption(Options.HELP.getOptionName());
        }

        final SchemaService schemaService = SyncopeServices.get(SchemaService.class);
        switch (Options.fromName(input.getOption())) {
            case LIST:
                final String listErrorMessage = "Usage: schema --list {SCHEMA-TYPE}\n"
                        + "   Schema type: PLAIN / DERIVED / VIRTUAL";
                if (parameters.length == 1) {
                    try {
                        final SchemaType schemaType = SchemaType.valueOf(input.firstParameter());
                        for (final AbstractSchemaTO schemaTO : schemaService.list(schemaType)) {
                            switch (schemaType) {
                                case PLAIN:
                                    System.out.println(" - Schema key: " + ((PlainSchemaTO) schemaTO).getKey());
                                    System.out.println("      type: " + ((PlainSchemaTO) schemaTO).getType());
                                    System.out.println("      is mandatory: "
                                            + ((PlainSchemaTO) schemaTO).getMandatoryCondition());
                                    break;
                                case DERIVED:
                                    System.out.println(" - Schema key: " + ((DerSchemaTO) schemaTO).getKey());
                                    System.out.println("      expression: " + ((DerSchemaTO) schemaTO).getExpression());
                                    break;
                                case VIRTUAL:
                                    System.out.println(" - Schema key: " + ((VirSchemaTO) schemaTO).getKey());
                                    break;
                                default:
                                    break;
                            }
                        }
                    } catch (final SyncopeClientException ex) {
                        System.out.println(" - Error: " + ex.getMessage());
                    } catch (final IllegalArgumentException ex) {
                        System.out.println(" - Error: " + input.firstParameter()
                                + " isn't a valid schema type, try with:");
                        for (final SchemaType type : SchemaType.values()) {
                            System.out.println("  *** " + type.name());
                        }
                    }
                } else {
                    System.out.println(listErrorMessage);
                }
                break;
            case LIST_ALL:
                try {
                    for (final SchemaType value : SchemaType.values()) {
                        System.out.println("Schemas for " + value);
                        for (final AbstractSchemaTO schemaTO : schemaService.list(value)) {
                            System.out.println("   - Name: " + schemaTO.getKey() + " type: "
                                    + schemaTO.getAnyTypeClass());
                        }
                    }
                } catch (final SyncopeClientException | WebServiceException ex) {
                    System.out.println(" - Error: " + ex.getMessage());
                }
                break;
            case READ:
                final String readErrorMessage = "Usage: schema --read {SCHEMA-TYPE} {SCHEMA-KEY}\n"
                        + "   Schema type: PLAIN / DERIVED / VIRTUAL";
                if (parameters.length >= 2) {
                    parameters = Arrays.copyOfRange(parameters, 1, parameters.length);
                    try {
                        final SchemaType schemaType = SchemaType.valueOf(input.firstParameter());
                        for (final String parameter : parameters) {
                            final AbstractSchemaTO schemaTO = schemaService.read(schemaType, parameter);
                            switch (schemaType) {
                                case PLAIN:
                                    System.out.println(" - Schema key: " + ((PlainSchemaTO) schemaTO).getKey());
                                    System.out.println("      any type class: "
                                            + ((PlainSchemaTO) schemaTO).getAnyTypeClass());
                                    System.out.println("      conversion pattern: "
                                            + ((PlainSchemaTO) schemaTO).getConversionPattern());
                                    System.out.println("      enumeration keys: "
                                            + ((PlainSchemaTO) schemaTO).getEnumerationKeys());
                                    System.out.println("      enumeration values: "
                                            + ((PlainSchemaTO) schemaTO).getEnumerationValues());
                                    System.out.println("      mandatory condition: "
                                            + ((PlainSchemaTO) schemaTO).getMandatoryCondition());
                                    System.out.println("      mime type: " + ((PlainSchemaTO) schemaTO).getMimeType());
                                    System.out.println("      secret key: "
                                            + ((PlainSchemaTO) schemaTO).getSecretKey());
                                    System.out.println("      validator class: "
                                            + ((PlainSchemaTO) schemaTO).getValidatorClass());
                                    System.out.println("      cipher algorithm: "
                                            + ((PlainSchemaTO) schemaTO).getCipherAlgorithm());
                                    System.out.println("      TYPE: "
                                            + ((PlainSchemaTO) schemaTO).getType());
                                    break;
                                case DERIVED:
                                    System.out.println(" - Schema key: " + ((DerSchemaTO) schemaTO).getKey());
                                    System.out.println("      any type class: "
                                            + ((DerSchemaTO) schemaTO).getAnyTypeClass());
                                    System.out.println("      expression: " + ((DerSchemaTO) schemaTO).getExpression());
                                    break;
                                case VIRTUAL:
                                    System.out.println(" - Schema key: " + ((VirSchemaTO) schemaTO).getKey());
                                    System.out.println("      any type class: "
                                            + ((VirSchemaTO) schemaTO).getAnyTypeClass());
                                    break;
                                default:
                                    break;
                            }
                        }
                    } catch (final SyncopeClientException | WebServiceException ex) {
                        if (ex.getMessage().startsWith("NotFound")) {
                            System.out.println(" - Schema " + parameters[0] + " doesn't exists!");
                        } else if (ex.getMessage().startsWith("DataIntegrityViolation")) {
                            System.out.println(" - You cannot delete schema " + parameters[0]);
                        } else {
                            System.out.println(ex.getMessage());
                        }
                    } catch (final IllegalArgumentException ex) {
                        System.out.println(" - Error: " + parameters[0] + " isn't a valid schema type, try with:");
                        for (final SchemaType type : SchemaType.values()) {
                            System.out.println("  *** " + type.name());
                        }
                    }
                } else {
                    System.out.println(readErrorMessage);
                }
                break;
            case DELETE:
                final String deleteErrorMessage = "Usage: schema --delete {SCHEMA-TYPE} {SCHEMA-KEY}\n"
                        + "   Schema type: PLAIN / DERIVED / VIRTUAL";
                if (parameters.length >= 2) {
                    parameters = Arrays.copyOfRange(parameters, 1, parameters.length);
                    try {
                        for (final String parameter : parameters) {
                            schemaService.delete(SchemaType.valueOf(input.firstParameter()), parameter);
                            System.out.println("Schema " + parameter + " successfully deleted!");
                        }
                    } catch (final SyncopeClientException | WebServiceException ex) {
                        if (ex.getMessage().startsWith("NotFound")) {
                            System.out.println(" - Schema " + parameters[0] + " doesn't exists!");
                        } else if (ex.getMessage().startsWith("DataIntegrityViolation")) {
                            System.out.println(" - You cannot delete schema " + parameters[0]);
                        } else {
                            System.out.println(ex.getMessage());
                        }
                    } catch (final IllegalArgumentException ex) {
                        System.out.println(" - Error: " + parameters[0] + " isn't a valid schema type, try with:");
                        for (final SchemaType type : SchemaType.values()) {
                            System.out.println("  *** " + type.name());
                        }
                    }
                } else {
                    System.out.println(deleteErrorMessage);
                }
                break;
            case HELP:
                System.out.println(HELP_MESSAGE);
                break;
            default:
                System.out.println(input.getOption() + " is not a valid option.");
                System.out.println("");
                System.out.println(HELP_MESSAGE);
        }
    }

    private enum Options {

        HELP("--help"),
        LIST("--list"),
        LIST_ALL("--list-all"),
        READ("--read"),
        DELETE("--delete");

        private final String optionName;

        private Options(final String optionName) {
            this.optionName = optionName;
        }

        public String getOptionName() {
            return optionName;
        }

        public boolean equalsOptionName(final String otherName) {
            return (otherName == null) ? false : optionName.equals(otherName);
        }

        public static Options fromName(final String name) {
            Options optionToReturn = HELP;
            for (final Options option : Options.values()) {
                if (option.equalsOptionName(name)) {
                    optionToReturn = option;
                }
            }
            return optionToReturn;
        }
    }
}
