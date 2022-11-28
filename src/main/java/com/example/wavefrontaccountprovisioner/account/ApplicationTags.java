package com.example.wavefrontaccountprovisioner.account;

import org.springframework.lang.Nullable;

/**
 * @author Moritz Halbritter
 */
public record ApplicationTags(String application, String service, @Nullable String cluster, @Nullable String shard) {
}
