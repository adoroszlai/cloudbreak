package com.sequenceiq.cloudbreak.domain;

public enum ResourceType {
    // AZURE
    AZURE_VIRTUAL_MACHINE,
    AZURE_CLOUD_SERVICE,
    AZURE_BLOB,
    AZURE_STORAGE,
    AZURE_NETWORK,
    AZURE_AFFINITY_GROUP,
    AZURE_SERVICE_CERTIFICATE,

    // AWS
    CLOUDFORMATION_STACK,

    // OPENSTACK
    HEAT_STACK,

    // GCC
    GCC_DISK,
    GCC_ATTACHED_DISK,
    GCC_NETWORK,
    GCC_FIREWALL_IN,
    GCC_FIREWALL_OUT,
    GCC_ROUTE,
    GCC_INSTANCE,
}
