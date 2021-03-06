package com.sequenceiq.cloudbreak.converter.v2.filesystem;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.v2.filesystem.WasbCloudStorageParameters;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.api.model.filesystem.WasbFileSystem;

@Component
public class WasbCloudStorageParametersToWasbFileSystemConverter
        extends AbstractConversionServiceAwareConverter<WasbCloudStorageParameters, WasbFileSystem> {

    @Override
    public WasbFileSystem convert(WasbCloudStorageParameters source) {
        WasbFileSystem fileSystemConfigurations = new WasbFileSystem();
        fileSystemConfigurations.setSecure(source.isSecure());
        fileSystemConfigurations.setAccountName(source.getAccountName());
        fileSystemConfigurations.setAccountKey(source.getAccountKey());
        return fileSystemConfigurations;
    }
}
