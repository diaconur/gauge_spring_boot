package com.gauge.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageId {

    UBUNTU_SERVER_2204("Ubuntu Server 22.04 LTS","ami-04e601abe3e1a910f");

    private final String imageType;
    private final String amiId;
}
