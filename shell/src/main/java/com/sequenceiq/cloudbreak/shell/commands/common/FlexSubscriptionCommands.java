package com.sequenceiq.cloudbreak.shell.commands.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;

import com.sequenceiq.cloudbreak.api.model.FlexSubscriptionRequest;
import com.sequenceiq.cloudbreak.api.model.FlexSubscriptionResponse;
import com.sequenceiq.cloudbreak.shell.model.OutPutType;
import com.sequenceiq.cloudbreak.shell.model.ShellContext;

public class FlexSubscriptionCommands implements CommandMarker {

    private ShellContext shellContext;

    public FlexSubscriptionCommands(ShellContext shellContext) {
        this.shellContext = shellContext;
    }

    @CliAvailabilityIndicator(value = { "flex register" })
    public boolean registerAvailable() {
        try {
            return shellContext.getSmartSenseSubscription() != null && !shellContext.getSmartSenseSubscription().isAutoGenerated();
        } catch (Exception ex) {
            return false;
        }
    }

    @CliCommand(value = "flex register", help = "Register a Flex subscription")
    public String register(
            @CliOption(key = "name", mandatory = true) String name,
            @CliOption(key = "subscriptionId", mandatory = true) String subscriptionId,
            @CliOption(key = "publicInAccount", help = "Flags if the blueprint is public in the account",
                    unspecifiedDefaultValue = "false", specifiedDefaultValue = "true") boolean publicInAccount) {
        FlexSubscriptionRequest request = new FlexSubscriptionRequest();
        request.setName(name);
        request.setSubscriptionId(subscriptionId);
        request.setSmartSenseSubscriptionId(shellContext.getSmartSenseSubscription().getId());
        try {
            Long id;
            if (publicInAccount) {
                id = shellContext.cloudbreakClient().flexSubscriptionEndpoint().postPublic(request).getId();
            } else {
                id = shellContext.cloudbreakClient().flexSubscriptionEndpoint().postPrivate(request).getId();
            }
            return String.format("Flex subscription registered with id: '%d'", id);
        } catch (Exception e) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(e);
        }
    }

    @CliAvailabilityIndicator(value = "flex set-default")
    public boolean setDefaultAvailable() {
        return true;
    }

    @CliCommand(value = "flex set-default", help = "Sets the default Flex subscription")
    public String setDefault(@CliOption(key = "name", mandatory = true) String name) {
        try {
            shellContext.cloudbreakClient().flexSubscriptionEndpoint().setDefaultInAccount(name);
            return String.format("Default Flex subscription changed'", name);
        } catch (Exception e) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(e);
        }
    }

    @CliAvailabilityIndicator(value = "flex use-for-controller")
    public boolean useForControllerAvailable() {
        return true;
    }

    @CliCommand(value = "flex use-for-controller", help = "Sets the Flex subscription for controller node")
    public String useForController(@CliOption(key = "name", mandatory = true) String name) {
        try {
            shellContext.cloudbreakClient().flexSubscriptionEndpoint().setUsedForControllerInAccount(name);
            return String.format("Flex subscription for controller changed'", name);
        } catch (Exception e) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(e);
        }
    }

    @CliAvailabilityIndicator(value = "flex list")
    public boolean listAvailable() {
        return true;
    }

    @CliCommand(value = "flex list", help = "Shows the currently available Flex subscriptions")
    public String list() throws Exception {
        try {
            List<FlexSubscriptionResponse> publics = shellContext.cloudbreakClient().flexSubscriptionEndpoint().getPublics();
            return shellContext.outputTransformer().render(shellContext.responseTransformer().transformToMap(publics, "id", "name"), "ID", "INFO");
        } catch (Exception ex) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(ex);
        }
    }

    @CliAvailabilityIndicator(value = { "flex delete --id", "flex delete --name" })
    public boolean deleteAvailable() {
        return true;
    }

    private String delete(Long id, String name) {
        try {
            if (id != null) {
                shellContext.cloudbreakClient().flexSubscriptionEndpoint().delete(id);
                return String.format("Flex subscription deleted with id: %s", id);
            } else if (name != null) {
                shellContext.cloudbreakClient().flexSubscriptionEndpoint().deletePrivate(name);
                return String.format("Flex subscription deleted with name: %s", name);
            }
            throw shellContext.exceptionTransformer().transformToRuntimeException("Id or subscription id not specified");
        } catch (Exception ex) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(ex);
        }
    }

    @CliCommand(value = "flex delete --id", help = "Deletes Flex subscription by its id")
    public String deleteById(@CliOption(key = "", mandatory = true) Long id) throws Exception {
        return delete(id, null);
    }

    @CliCommand(value = "flex delete --name", help = "Deletes Flex subscription by its name")
    public String deleteByName(@CliOption(key = "", mandatory = true) String name) throws Exception {
        return delete(null, name);
    }

    @CliAvailabilityIndicator(value = { "flex show --id", "flex show --name" })
    public boolean showAvailable() {
        return true;
    }

    private String show(Long id, String name, OutPutType outPutType) {
        try {
            FlexSubscriptionResponse flexSubscriptionResponse;
            if (id != null) {
                flexSubscriptionResponse = shellContext.cloudbreakClient().flexSubscriptionEndpoint().get(id);
            } else if (name != null) {
                flexSubscriptionResponse = shellContext.cloudbreakClient().flexSubscriptionEndpoint().getPublic(name);
            } else {
                throw shellContext.exceptionTransformer().transformToRuntimeException("Id or subscription id not specified");
            }

            Map<String, String> map = new HashMap<>();
            map.put("id", flexSubscriptionResponse.getId().toString());
            map.put("name", flexSubscriptionResponse.getName());
            map.put("subscriptionId", flexSubscriptionResponse.getSubscriptionId());
            map.put("smartSenseSubscription", flexSubscriptionResponse.getSmartSenseSubscription().getSubscriptionId());
            map.put("isDefault", Boolean.toString(flexSubscriptionResponse.isDefault()));
            map.put("isUsedForController", Boolean.toString(flexSubscriptionResponse.isUsedForController()));

            return shellContext.outputTransformer().render(outPutType, map, "FIELD", "INFO");
        } catch (Exception ex) {
            throw shellContext.exceptionTransformer().transformToRuntimeException(ex);
        }
    }

    @CliCommand(value = "flex show --id", help = "Describes Flex subscription by its id")
    public String showById(@CliOption(key = "", mandatory = true) Long id,
            @CliOption(key = "outputType", help = "OutputType of the response") OutPutType outPutType) throws Exception {
        return show(id, null, outPutType);
    }

    @CliCommand(value = "flex show --name", help = "Describes Flex subscription by its name")
    public String showByName(@CliOption(key = "", mandatory = true) String name,
            @CliOption(key = "outputType", help = "OutputType of the response") OutPutType outPutType) throws Exception {
        return show(null, name, outPutType);
    }
}